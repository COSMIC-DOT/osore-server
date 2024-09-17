package com.dot.osore.domain.note.service;

import com.dot.osore.domain.note.dto.NoteInfoResponse;
import com.dot.osore.domain.note.dto.NoteRequest;
import com.dot.osore.domain.note.dto.NoteResponse;
import com.dot.osore.domain.note.dto.NoteListResponse;
import com.dot.osore.domain.note.entity.Note;
import com.dot.osore.domain.note.repository.NoteRepository;
import com.dot.osore.domain.member.entity.User;
import com.dot.osore.domain.member.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {
    final private UserRepository userRepository;
    final private NoteRepository noteRepository;

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

    private NoteResponse getNoteResponse(Note note) throws Exception {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(note.getId());
        noteResponse.setTitle(note.getTitle());

        String repository = parseRepoName(note.getUrl());
        noteResponse.setRepository(repository);

        GitHub github = GitHub.connectUsingOAuth(token);
        GHRepository repo = github.getRepository(repository);

        noteResponse.setAvatar(repo.getOwner().getAvatarUrl());
        noteResponse.setDescription(repo.getDescription());
        noteResponse.setContributors(List.of(repo.listContributors()).size());
        noteResponse.setStars(repo.getStargazersCount());
        noteResponse.setForks(repo.getForksCount());
        return noteResponse;
    }

    public NoteListResponse findByUserId(Long id) throws Exception {
        List<Note> notes = noteRepository.findByUser_UserId(id);
        List<NoteResponse> list = new ArrayList<>();

        for (Note note: notes) {
            NoteResponse noteResponse = getNoteResponse(note);
            list.add(noteResponse);
        }
        NoteListResponse result = NoteListResponse.builder().list(list).build();
        return result;
    }

    public void saveNote(Long id, NoteRequest note) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        Note savedNote = Note.builder().note(note).user(user).build();
        noteRepository.save(savedNote);
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }
}
