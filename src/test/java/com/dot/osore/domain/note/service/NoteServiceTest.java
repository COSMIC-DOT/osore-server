package com.dot.osore.domain.note.service;

import static org.junit.jupiter.api.Assertions.*;

import com.dot.osore.context.TestContext;
import com.dot.osore.domain.member.entity.User;
import com.dot.osore.domain.note.dto.NoteRequest;
import com.dot.osore.domain.note.dto.NoteResponse;
import com.dot.osore.domain.note.entity.Note;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NoteServiceTest extends TestContext {

    @Nested
    class getNoteList_메소드는 {

        @Test
        void 사용자_Id를_통해_노트_정보들을_가져온다() {
            // given
            User user = User.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            User savedUser = userRepository.save(user);

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
                    .user(savedUser)
                    .build();

            Note testNote2 = Note.builder()
                    .url("test2")
                    .title("test2")
                    .avatar("test2")
                    .description("test2")
                    .contributorsCount(2)
                    .starsCount(2)
                    .forksCount(2)
                    .branch("test2")
                    .version("test2")
                    .user(savedUser)
                    .build();
            noteRepository.save(testNote1);
            noteRepository.save(testNote2);

            // when
            List<NoteResponse> noteList = noteService.getNoteList(savedUser.getId());

            // then
            assertEquals(2, noteList.size());
        }
    }

    @Nested
    class saveNote_메소드는 {

        @Test
        void 노트_정보를_저장한다() throws Exception {
            // given
            User user = User.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            User savedUser = userRepository.save(user);

            String url = "https://github.com/woowa-techcamp-2024/Team7-ELEVEN";
            NoteRequest noteRequest = new NoteRequest(url, "test", "test", "test");

            // when
            noteService.saveNote(savedUser.getId(), noteRequest);

            // then
            Note note = noteRepository.findById(1L).orElse(null);
            assertAll(
                    () -> assertEquals("test", note.getTitle()),
                    () -> assertEquals(4, note.getContributorsCount()),
                    () -> assertEquals(7, note.getStarsCount()),
                    () -> assertEquals(3, note.getForksCount()),
                    () -> assertEquals("test", note.getBranch()),
                    () -> assertEquals("test", note.getVersion())
            );
        }
    }

    @Nested
    class deleteNote_메소드는 {

        @Test
        void 노트_정보를_삭제한다() {
            // given
            User user = User.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            User savedUser = userRepository.save(user);

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
                    .user(savedUser)
                    .build();
            Note savedNote = noteRepository.save(testNote1);

            // when
            noteService.deleteNote(savedUser.getId(), savedNote.getId());

            // then
            assertFalse(noteRepository.findById(savedNote.getId()).isPresent());
        }
    }
}