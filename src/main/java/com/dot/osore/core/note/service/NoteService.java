package com.dot.osore.core.note.service;

import static com.dot.osore.global.github.GithubParser.parseRepoName;

import com.dot.osore.core.file.service.FileService;
import com.dot.osore.core.member.entity.Member;
import com.dot.osore.core.member.service.MemberService;
import com.dot.osore.core.note.dto.NoteRequest;
import com.dot.osore.core.note.dto.DetailNoteResponse;
import com.dot.osore.core.note.dto.SimpleNoteResponse;
import com.dot.osore.core.note.entity.Note;
import com.dot.osore.core.note.repository.NoteRepository;
import com.dot.osore.global.github.GithubConnector;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final MemberService memberService;
    private final FileService fileService;
    private final NoteRepository noteRepository;
    private final GithubConnector githubConnector;

    /**
     * 사용자 Id를 통해 노트 정보들을 가져오는 메소드
     *
     * @param signInId 사용자 Id
     */
    public List<DetailNoteResponse> getNoteList(Long signInId) {
        List<Note> notes = noteRepository.findByMember_Id(signInId);
        List<DetailNoteResponse> notesResponse = new ArrayList<>();
        notes.forEach(note ->
                notesResponse.add(DetailNoteResponse.from(note)));
        return notesResponse;
    }

    /**
     * 노트 정보를 가져오는 메소드
     *
     * @param noteId 노트 Id
     */
    public SimpleNoteResponse getNote(Long noteId) throws Exception {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("해당 노트를 찾을 수 없습니다."));
        return SimpleNoteResponse.from(note);
    }

    /**
     * 노트 정보를 저장하는 메소드
     *
     * @param signInId 사용자 Id
     * @param note     노트 정보
     */
    public void saveNote(Long signInId, NoteRequest note) throws Exception {
        Member member = memberService.findById(signInId);
        String repository = parseRepoName(note.url());

        GitHub github = githubConnector.getGithubInstance();
        GHRepository repo = github.getRepository(repository);

        String avatar = repo.getOwner().getAvatarUrl();
        String description = repo.getDescription();
        Integer contributorsCount = repo.listContributors().toList().size();
        Integer starsCount = repo.getStargazersCount();
        Integer forksCount = repo.getForksCount();

        Note savedNote = noteRepository.save(Note.builder()
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
                .build());
        fileService.saveRepositoryFiles(note.url(), note.branch(), savedNote);
    }

    /**
     * 노트 정보를 삭제하는 메소드
     *
     * @param noteId 노트 Id
     */
    @Transactional
    public void deleteNote(Long noteId) {
        fileService.deleteByNoteId(noteId);
        noteRepository.deleteById(noteId);
    }

    /**
     * 노트 이름을 수정하는 메소드
     *
     * @param noteId 노트 Id
     * @param title  수정할 이름
     */
    @Transactional
    public void updateNoteTitle(Long noteId, String title) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("해당 노트를 찾을 수 없습니다."));
        note.setTitle(title);
    }
}
