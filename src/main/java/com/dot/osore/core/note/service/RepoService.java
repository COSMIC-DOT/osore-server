package com.dot.osore.core.note.service;

import static com.dot.osore.global.github.UrlParser.parseRepoName;

import com.dot.osore.core.note.dto.RepoInfoResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RepoService {

    @Value("${client.github.token}")
    private String token;

    /**
     * 깃허브 저장소 URL을 받아 브랜치와 버전을 반환하는 메서드
     *
     * @param url 깃허브 저장소 URL
     */
    public RepoInfoResponse getGithubRepositoryInfo(String url) throws Exception {
        GitHub github = GitHub.connectUsingOAuth(token);
        GHRepository repo = github.getRepository(parseRepoName(url));

        CompletableFuture<List<String>> branchesFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return getRepositoryBranches(repo);
            } catch (IOException e) {
                throw new RuntimeException("Failed to fetch branches", e);
            }
        });

        CompletableFuture<List<String>> versionsFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return getRepositoryVersions(repo);
            } catch (IOException e) {
                throw new RuntimeException("Failed to fetch versions", e);
            }
        });

        List<String> branches = branchesFuture.join();
        List<String> versions = versionsFuture.join();

        RepoInfoResponse result = new RepoInfoResponse(branches, versions);
        return result;
    }

    private List<String> getRepositoryVersions(GHRepository repo) throws IOException {
        List<String> versions = new ArrayList<>();
        PagedIterable<GHTag> githubRepositoryTags = repo.listTags();
        for (GHTag tag : githubRepositoryTags) {
            versions.add(tag.getName());
        }
        return versions;
    }

    private List<String> getRepositoryBranches(GHRepository repo) throws IOException {
        List<String> branches = new ArrayList<>();
        Map<String, GHBranch> githubRepositoryBranches = repo.getBranches();
        for (Map.Entry<String, GHBranch> branch : githubRepositoryBranches.entrySet()) {
            branches.add(branch.getKey());
        }
        return branches;
    }
}
