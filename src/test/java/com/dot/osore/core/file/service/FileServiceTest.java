package com.dot.osore.core.file.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dot.osore.context.TestContext;
import com.dot.osore.core.file.entity.File;
import com.dot.osore.core.member.entity.Member;
import com.dot.osore.core.note.entity.Note;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FileServiceTest extends TestContext {

    @Nested
    class saveRepositoryFiles_메서드는 {

        @Test
        void 주어진_저장소_URL과_브랜치_이름으로_깃허브_저장소의_파일들을_저장한다() throws Exception {
            // given
            Member member = Member.builder()
                    .name("test")
                    .avatar("test")
                    .build();
            Member savedMember = memberRepository.save(member);
            Note note = Note.builder()
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
            Note savedNote = noteRepository.save(note);

            String url = "https://github.com/minseok-oh/minseok-oh";
            String branch = "main";

            // when
            fileService.saveRepositoryFiles(url, branch, savedNote);
            List<File> files = fileRepository.findByNote_Id(1L);

            // then
            assertEquals(1, files.size());
        }
    }
}