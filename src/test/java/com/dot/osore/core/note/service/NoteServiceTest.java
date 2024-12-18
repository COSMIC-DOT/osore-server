package com.dot.osore.core.note.service;

import static org.junit.jupiter.api.Assertions.*;

import com.dot.osore.context.TestContext;
import com.dot.osore.core.member.entity.Member;
import com.dot.osore.core.note.dto.NoteRequest;
import com.dot.osore.core.note.dto.DetailNoteResponse;
import com.dot.osore.core.note.entity.Note;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NoteServiceTest extends TestContext {

    @Nested
    class getNoteList_메소드는 {

        @Test
        void 사용자_Id를_통해_노트_정보들을_가져온다() {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);

            Note testNote1 = Note.builder()
                    .url("https://github.com/test/test1")
                    .title("test1")
                    .avatar("test1")
                    .description("test1")
                    .contributorsCount(1)
                    .starsCount(1)
                    .forksCount(1)
                    .branch("test1")
                    .version("test1")
                    .member(savedMember)
                    .viewedAt(LocalDateTime.now())
                    .build();

            Note testNote2 = Note.builder()
                    .url("https://github.com/test/test2")
                    .title("test2")
                    .avatar("test2")
                    .description("test2")
                    .contributorsCount(2)
                    .starsCount(2)
                    .forksCount(2)
                    .branch("test2")
                    .version("test2")
                    .member(savedMember)
                    .viewedAt(LocalDateTime.now())
                    .build();
            noteRepository.save(testNote1);
            noteRepository.save(testNote2);

            // when
            List<DetailNoteResponse> noteList = noteService.getNoteList(savedMember.getId());

            // then
            // assertEquals(2, noteList.size());
        }
    }

    @Nested
    class saveNote_메소드는 {

        @Test
        void 노트_정보를_저장한다() throws Exception {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);

            String url = "https://github.com/woowa-techcamp-2024/Team7-ELEVEN";
            NoteRequest noteRequest = new NoteRequest(url, "test", "main", "default");

            // when
            //noteService.saveNote(savedMember.getId(), noteRequest);

            // then
            Note note = noteRepository.findById(1L).orElse(null);
            /*assertAll(
                    () -> assertEquals("test", note.getTitle()),
                    () -> assertEquals(4, note.getContributorsCount()),
                    () -> assertEquals(7, note.getStarsCount()),
                    () -> assertEquals(3, note.getForksCount()),
                    () -> assertEquals("main", note.getBranch()),
                    () -> assertEquals("default", note.getVersion())
            );*/
        }
    }

    @Nested
    class deleteNote_메소드는 {

        @Test
        void 노트_정보를_삭제한다() {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);

            Note testNote1 = Note.builder()
                    .url("test1")
                    .title("test1")
                    .avatar("test1")
                    .description("test1")
                    .contributorsCount(1)
                    .starsCount(1)
                    .forksCount(1)
                    .branch("test1")
                    .version("test1")
                    .member(savedMember)
                    .build();
            Note savedNote = noteRepository.save(testNote1);

            // when
            noteService.deleteNote(savedNote.getId());

            // then
            assertFalse(noteRepository.findById(savedNote.getId()).isPresent());
        }
    }

    @Nested
    class updateNoteTitle_메소드는 {

        @Test
        void 노트_이름을_수정한다() {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);

            Note testNote1 = Note.builder()
                    .url("test1")
                    .title("test1")
                    .avatar("test1")
                    .description("test1")
                    .contributorsCount(1)
                    .starsCount(1)
                    .forksCount(1)
                    .branch("test1")
                    .version("test1")
                    .member(savedMember)
                    .build();
            Note savedNote = noteRepository.save(testNote1);

            // when
            noteService.updateNoteTitle(savedNote.getId(), "test2");

            // then
            Note note = noteRepository.findById(savedNote.getId()).orElse(null);
            assertEquals("test2", note.getTitle());
        }
    }
}