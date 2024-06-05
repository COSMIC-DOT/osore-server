package com.dot.osore.domain.note.service;

import com.dot.osore.domain.note.dto.NoteInfoResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    @Value("${client.github.token}")
    private String token;

    private String parseRepoName(String url) throws Exception {
        List<String> words = List.of(url.split("/"));
        if (!"github.com".matches(words.get(2))) throw new Exception();
        return words.get(3) + "/" + words.get(4);
    }

    public NoteInfoResponse getNoteInfo(String url) throws Exception {
        GitHub github = GitHub.connectUsingOAuth(token);
        GHRepository repo = github.getRepository(parseRepoName(url));

        Map<String, GHBranch> branches = repo.getBranches();
        List<String> branch = new ArrayList<>();
        for (Map.Entry<String, GHBranch> entry : branches.entrySet()) {
            branch.add(entry.getKey());
        }

        List<String> version = new ArrayList<>();
        PagedIterable<GHTag> tags = repo.listTags();
        for (GHTag tag : tags) {
            version.add(tag.getName());
        }
        for (String b: version) System.out.println(b);

        NoteInfoResponse result = NoteInfoResponse.builder().branch(branch).version(version).build();
        return result;
    }
}
