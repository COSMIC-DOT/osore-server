package com.dot.osore.core.file.service;

import static com.dot.osore.global.parser.GithubParser.parseRepoName;

import com.dot.osore.core.file.entity.File;
import com.dot.osore.core.file.repository.FileRepository;
import com.dot.osore.core.note.entity.Note;
import java.io.IOException;
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
                System.out.println(content.getPath());
                saveFileToDatabase(content.getPath(), fileContent, note);
            } else if (content.isDirectory()) {
                fetchFilesFromDirectory(repo, content.getPath(), branch, note);
            }
        }
    }

    private void saveFileToDatabase(String path, String content, Note note) {
        File file = new File(path, content, note);
        fileRepository.save(file);
    }
}
