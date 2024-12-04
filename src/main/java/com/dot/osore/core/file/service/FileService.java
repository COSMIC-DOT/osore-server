package com.dot.osore.core.file.service;

import static com.dot.osore.global.github.GithubParser.parseRepoName;

import com.dot.osore.core.file.dto.DetailFileInfoResponse;
import com.dot.osore.core.file.dto.SimpleFileInfoResponse;
import com.dot.osore.core.file.entity.File;
import com.dot.osore.core.file.repository.FileRepository;
import com.dot.osore.core.note.entity.Note;
import com.dot.osore.global.github.GithubConnector;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final GithubConnector githubConnector;
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private static final Logger logger = Logger.getLogger(FileService.class.getName());

    /**
     * 깃허브 저장소의 파일들을 저장하는 메서드
     *
     * @param url    깃허브 저장소 URL
     * @param branch 브랜치 이름
     */
    public void saveRepositoryFiles(String url, String branch, Note note) throws Exception {
        String localRepoPath = "temp/" + note.getId();  // 로컬 경로 설정 (각 노트에 대해 고유 경로 사용)

        // 레포지토리 클론
        cloneRepository(url, branch, localRepoPath);

        // 파일을 데이터베이스에 저장
        saveFilesToDatabase(new java.io.File(localRepoPath), note);

        // 로컬에 저장된 클론 디렉토리 삭제
        deleteLocalRepository(new java.io.File(localRepoPath));
    }

    private void cloneRepository(String url, String branch, String localRepoPath) throws GitAPIException {
        // 지정된 URL과 브랜치로 레포지토리 클론
        Git.cloneRepository()
                .setURI(url)
                .setBranch(branch)
                .setDirectory(new java.io.File(localRepoPath))
                .call();
    }

    private void saveFilesToDatabase(java.io.File directory, Note note) {
        saveFilesRecursively(directory, note);
        fileRepository.flush();  // 모든 파일을 저장한 후 flush
    }

    private void saveFilesRecursively(java.io.File directory, Note note) {
        String basePath = Paths.get("temp", String.valueOf(note.getId())).toString();

        for (java.io.File file : directory.listFiles()) {
            if (file.isDirectory()) {
                // 하위 디렉토리를 재귀적으로 탐색
                saveFilesRecursively(file, note);
            } else {
                try {
                    // 파일 내용을 읽어 데이터베이스에 저장
                    String fileContent = new String(Files.readAllBytes(file.toPath()));
                    String relativePath = file.getPath().replaceFirst(basePath + java.io.File.separator, "");
                    File dbFile = new File(relativePath, fileContent, note);  // DB 저장용 파일 객체 생성
                    fileRepository.save(dbFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void deleteLocalRepository(java.io.File directory) {
        if (directory.isDirectory()) {
            for (java.io.File file : directory.listFiles()) {
                deleteLocalRepository(file);  // 재귀적으로 파일 삭제
            }
        }
        directory.delete();  // 파일 또는 빈 디렉토리 삭제
    }

    /**
     * 노트에 저장된 파일 정보를 가져와 프론트에 필요한 양식대로 반환하는 메서드
     *
     * @param noteId 노트 ID
     */
    public SimpleFileInfoResponse getSimpleFileInfoList(Long noteId) {
        List<File> files = fileRepository.findByNote_Id(noteId);
        SimpleFileInfoResponse root = new SimpleFileInfoResponse(null, "folder", "root", null, new TreeSet<>());

        for (File file : files) {
            String path = file.getPath();
            String[] parts = path.split("/");
            SimpleFileInfoResponse current = root;

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                String fileName;
                String extension;

                boolean isFile = i == parts.length - 1;
                if (!part.contains(".")) {
                    fileName = part;
                    extension = null;
                } else {
                    fileName = isFile ? part.substring(0, part.lastIndexOf(".")) : part;
                    extension = isFile ? part.substring(part.lastIndexOf(".") + 1) : null;
                }

                SimpleFileInfoResponse child = current.findChildByName(fileName);
                if (child == null) {
                    child = new SimpleFileInfoResponse(
                            isFile ? file.getId() : null,
                            isFile ? "file" : "folder",
                            fileName, extension,
                            new TreeSet<>());
                    current.children().add(child);
                }
                current = child;
            }
        }

        return root;
    }

    public DetailFileInfoResponse getDetailFileInfo(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow();
        return DetailFileInfoResponse.from(file);
    }

    /**
     * 노트에 저장된 파일 정보를 삭제하는 메서드
     *
     * @param noteId 노트 ID
     */
    public void deleteByNoteId(Long noteId) {
        fileRepository.deleteByNote_Id(noteId);
    }
}
