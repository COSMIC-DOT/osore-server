package com.dot.osore.core.memo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.dot.osore.context.TestContext;
import com.dot.osore.core.member.entity.Member;
import com.dot.osore.core.memo.entity.Memo;
import com.dot.osore.core.note.entity.Note;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemoServiceTest extends TestContext {

    @Nested
    class saveMemo_메서드는 {

        @Test
        void 메모를_저장한다() {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);

            Note note = Note.builder()
                    .url("https://github.com/test/test")
                    .title("test")
                    .avatar("test")
                    .description("test")
                    .contributorsCount(1)
                    .starsCount(1)
                    .forksCount(1)
                    .branch("test")
                    .version("test")
                    .member(savedMember)
                    .build();
            Note savedNote = noteRepository.save(note);

            // when
            memoService.saveMemo(savedNote.getId(), 1L, "test");

            // then
            Memo memo = memoRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("해당 메모가 존재하지 않습니다."));
            assertEquals("test", memo.getContent());
            assertEquals(1L, memo.getPage());
            assertEquals(savedNote.getId(), memo.getNote().getId());
        }
    }

    @Nested
    class getMemo_메서드는 {

        @Test
        void 메모를_조회한다() {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);

            Note note = Note.builder()
                    .url("https://github.com/test/test")
                    .title("test")
                    .avatar("test")
                    .description("test")
                    .contributorsCount(1)
                    .starsCount(1)
                    .forksCount(1)
                    .branch("test")
                    .version("test")
                    .member(savedMember)
                    .build();
            Note savedNote = noteRepository.save(note);

            Memo memo = Memo.builder()
                    .content("test content")
                    .note(savedNote)
                    .page(1L)
                    .build();
            Memo savedMemo = memoRepository.save(memo);

            // when
            String foundMemo = memoService.getMemo(savedMemo.getId());

            // then
            assertEquals("test content", foundMemo);
        }
    }

    @Nested
    class updateMemo_메서드는 {

        @Test
        void 메모를_수정한다() {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);

            Note note = Note.builder()
                    .url("https://github.com/test/test")
                    .title("test")
                    .avatar("test")
                    .description("test")
                    .contributorsCount(1)
                    .starsCount(1)
                    .forksCount(1)
                    .branch("test")
                    .version("test")
                    .member(savedMember)
                    .build();
            Note savedNote = noteRepository.save(note);

            Memo memo = Memo.builder()
                    .content("original content")
                    .note(savedNote)
                    .page(1L)
                    .build();
            Memo savedMemo = memoRepository.save(memo);

            // when
            String updatedContent = "updated content";
            memoService.updateMemo(savedMemo.getId(), updatedContent);

            // then
            Memo updatedMemo = memoRepository.findById(savedMemo.getId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 메모가 존재하지 않습니다."));
            assertEquals(updatedContent, updatedMemo.getContent());
        }
    }

    @Nested
    class deleteMemo_메서드는 {

        @Test
        void 메모를_삭제한다() {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);

            Note note = Note.builder()
                    .url("https://github.com/test/test")
                    .title("test")
                    .avatar("test")
                    .description("test")
                    .contributorsCount(1)
                    .starsCount(1)
                    .forksCount(1)
                    .branch("test")
                    .version("test")
                    .member(savedMember)
                    .build();
            Note savedNote = noteRepository.save(note);

            Memo memo = Memo.builder()
                    .content("to be deleted")
                    .note(savedNote)
                    .page(1L)
                    .build();
            Memo savedMemo = memoRepository.save(memo);

            // when
            memoService.deleteMemo(savedMemo.getId());

            // then
            assertFalse(memoRepository.findById(savedMemo.getId()).isPresent());
        }
    }
}