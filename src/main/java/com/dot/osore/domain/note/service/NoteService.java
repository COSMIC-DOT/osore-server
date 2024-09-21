package com.dot.osore.domain.note.service;

import static com.dot.osore.util.github.UrlParser.parseRepoName;

import com.dot.osore.domain.note.dto.RepoInfoResponse;
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

        int contributorsCount = repo.listContributors().toList().size();
        noteResponse.setContributors(contributorsCount);

        noteResponse.setStars(repo.getStargazersCount());
        noteResponse.setForks(repo.getForksCount());
        return noteResponse;
    }

    public NoteListResponse findByUserId(Long id) throws Exception {
        List<Note> notes = noteRepository.findByUser_Id(id);
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
