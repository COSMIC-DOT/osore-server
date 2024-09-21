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

    /**
     * 사용자 Id를 통해 노트 정보들을 가져오는 메소드
     *
     * @param signInId 사용자 Id
     */
    public List<NoteResponse> getNoteList(Long signInId) {
        List<Note> notes = noteRepository.findByUser_Id(signInId);
        List<NoteResponse> notesResponse = new ArrayList<>();
        notes.forEach(note ->
                notesResponse.add(NoteResponse.createNoteResponse(note)));
        return notesResponse;
    }

    /**
     * 노트 정보를 저장하는 메소드
     *
     * @param signInId 사용자 Id
     * @param note 노트 정보
     */
    public void saveNote(Long signInId, NoteRequest note) throws Exception {
        User user = userRepository.findById(signInId).orElse(null);
        String repository = parseRepoName(note.url());

        GitHub github = GitHub.connectUsingOAuth(token);
        GHRepository repo = github.getRepository(repository);

        String avatar = repo.getOwner().getAvatarUrl();
        String description = repo.getDescription();
        Integer contributorsCount = repo.listContributors().toList().size();
        Integer starsCount = repo.getStargazersCount();
        Integer forksCount = repo.getForksCount();

        Note savedNote = Note.builder()
                .url(note.url())
                .title(note.title())
                .avatar(avatar)
                .description(description)
                .contributorsCount(contributorsCount)
                .starsCount(starsCount)
                .forksCount(forksCount)
                .branch(note.branch())
                .version(note.version())
                .user(user)
                .build();
        noteRepository.save(savedNote);
    }

    /**
     * 노트 정보를 삭제하는 메소드
     *
     * @param signInId 사용자 Id
     * @param noteId 노트 Id
     */
    public void deleteNote(Long signInId, Long noteId) {
        noteRepository.deleteById(noteId);
    }
}
