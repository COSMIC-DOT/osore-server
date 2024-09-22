package com.dot.osore.core.file.service;

import static com.dot.osore.global.parser.GithubParser.parseRepoName;

import com.dot.osore.core.file.dto.FileInfoResponse;
import com.dot.osore.core.file.entity.File;
import com.dot.osore.core.file.repository.FileRepository;
import com.dot.osore.core.note.entity.Note;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Value("${client.github.token}")
    private String token;

    /**
     * 깃허브 저장소의 파일들을 저장하는 메서드
     *
     * @param url 깃허브 저장소 URL
     * @param branch 브랜치 이름
     */
    public void saveRepositoryFiles(String url, String branch, Note note) throws Exception {
        GitHub github = GitHub.connectUsingOAuth(token);
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
                fetchFilesFromDirectory(repo, content.getPath(), branch, note);
            }
        }
    }

    /**
     * 노트에 저장된 파일 정보를 가져와 프론트에 필요한 양식대로 반환하는 메서드
     *
     * @param noteId 노트 ID
     */
    public FileInfoResponse getFileInfoList(Long noteId) {
        List<File> files = fileRepository.findByNote_Id(noteId);
        FileInfoResponse root = new FileInfoResponse("folder", "root", null, new ArrayList<>());

        for (File file : files) {
            String path = file.getPath();
            String[] parts = path.split("/");
            FileInfoResponse current = root;

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                boolean isFile = part.contains(".");
                String fileName = isFile ? part.substring(0, part.lastIndexOf(".")) : part;
                String extension = isFile ? part.substring(part.lastIndexOf(".") + 1) : null;

                FileInfoResponse child = current.findChildByName(fileName);
                if (child == null) {
                    child = new FileInfoResponse(isFile ? "file" : "folder", fileName, extension, new ArrayList<>());
                    current.children().add(child);
                }
                current = child;
            }
        }

        return root;
    }
}
