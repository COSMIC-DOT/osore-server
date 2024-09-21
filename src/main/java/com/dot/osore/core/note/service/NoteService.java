package com.dot.osore.core.note.service;

import static com.dot.osore.global.github.UrlParser.parseRepoName;

import com.dot.osore.core.member.service.MemberService;
import com.dot.osore.core.note.dto.NoteRequest;
import com.dot.osore.core.note.dto.NoteResponse;
import com.dot.osore.core.note.entity.Note;
import com.dot.osore.core.note.repository.NoteRepository;
import com.dot.osore.core.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final MemberService memberService;
    private final NoteRepository noteRepository;

    @Value("${client.github.token}")
    private String token;

    /**
     * 사용자 Id를 통해 노트 정보들을 가져오는 메소드
     *
     * @param signInId 사용자 Id
     */
    public List<NoteResponse> getNoteList(Long signInId) {
        List<Note> notes = noteRepository.findByMember_Id(signInId);
        List<NoteResponse> notesResponse = new ArrayList<>();
        notes.forEach(note ->
                notesResponse.add(NoteResponse.from(note)));
        return notesResponse;
    }

    /**
     * 노트 정보를 저장하는 메소드
     *
     * @param signInId 사용자 Id
     * @param note 노트 정보
     */
    public void saveNote(Long signInId, NoteRequest note) throws Exception {
        Member member = memberService.findById(signInId);
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
                .member(member)
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
