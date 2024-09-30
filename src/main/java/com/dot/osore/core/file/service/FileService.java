package com.dot.osore.core.file.service;

import static com.dot.osore.global.github.GithubParser.parseRepoName;

import com.dot.osore.core.file.dto.DetailFileInfoResponse;
import com.dot.osore.core.file.dto.SimpleFileInfoResponse;
import com.dot.osore.core.file.entity.File;
import com.dot.osore.core.file.repository.FileRepository;
import com.dot.osore.core.note.entity.Note;
import com.dot.osore.global.github.GithubConnector;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final GithubConnector githubConnector;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 깃허브 저장소의 파일들을 저장하는 메서드
     *
     * @param url 깃허브 저장소 URL
     * @param branch 브랜치 이름
     */
    public void saveRepositoryFiles(String url, String branch, Note note) throws Exception {
        GitHub github = githubConnector.getGithubInstance();
        GHRepository repo = github.getRepository(parseRepoName(url));
        fetchFilesFromDirectory(repo, "", branch, note);
    }

    private void fetchFilesFromDirectory(GHRepository repo, String path, String branch, Note note)
            throws IOException {
        List<GHContent> contents = repo.getDirectoryContent(path, branch);

        for (GHContent content : contents) {
            if (content.isFile()) {
                String fileContent = content.getContent();
                File file = new File(content.getPath(), fileContent, note);
                fileRepository.save(file);
            } else if (content.isDirectory()) {
                executorService.execute(() -> {
                    try {
                        fetchFilesFromDirectory(repo, content.getPath(), branch, note);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    /**
     * 노트에 저장된 파일 정보를 가져와 프론트에 필요한 양식대로 반환하는 메서드
     *
     * @param noteId 노트 ID
     */
    public SimpleFileInfoResponse getSimpleFileInfoList(Long noteId) {
        List<File> files = fileRepository.findByNote_Id(noteId);
        SimpleFileInfoResponse root = new SimpleFileInfoResponse("folder", "root", null, null, new TreeSet<>());

        for (File file : files) {
            String path = file.getPath();
            String[] parts = path.split("/");
            SimpleFileInfoResponse current = root;

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                String fileName;
                String extension;

                boolean isFile = part.contains(".");
                if (!isFile && i == parts.length - 1) {
                    isFile = true;
                    fileName = part;
                    extension = null;
                } else {
                    fileName = isFile ? part.substring(0, part.lastIndexOf(".")) : part;
                    extension = isFile ? part.substring(part.lastIndexOf(".") + 1) : null;
                }

                SimpleFileInfoResponse child = current.findChildByName(fileName);
                if (child == null) {
                    child = new SimpleFileInfoResponse(isFile ? "file" : "folder",
                            fileName, extension,
                            isFile? path: null, new TreeSet<>());
                    current.children().add(child);
                }
                current = child;
            }
        }

        return root;
    }

    public DetailFileInfoResponse getDetailFileInfo(Long noteId, String filePath) {
        File file = fileRepository.findByNote_IdAndPath(noteId, filePath);
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
