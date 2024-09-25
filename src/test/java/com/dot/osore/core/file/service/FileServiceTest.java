package com.dot.osore.core.file.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dot.osore.context.TestContext;
import com.dot.osore.core.file.dto.SimpleFileInfoResponse;
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

    @Nested
    class getFileInfoList_메서드는 {

        @Test
        void 주어진_노트_ID에_해당하는_파일_정보를_가져온다() {
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

            File file = File.builder()
                    .path("src/Main.java")
                    .content("test1")
                    .note(savedNote)
                    .build();
            fileRepository.save(file);

            // when
            SimpleFileInfoResponse simpleFileInfoResponse = fileService.getSimpleFileInfoList(1L);

            // then=
            assertEquals("folder", simpleFileInfoResponse.type());
            assertEquals("root", simpleFileInfoResponse.name());
            assertEquals(1, simpleFileInfoResponse.children().size());
        }

        @Test
        void 주어진_노트_ID에_해당하는_파일_정보는_정렬되어_가져온다() {
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

            File file1 = File.builder()
                    .path("src/Main.java")
                    .content("test1")
                    .note(savedNote)
                    .build();
            fileRepository.save(file1);

            File file2 = File.builder()
                    .path("src/File.java")
                    .content("test1")
                    .note(savedNote)
                    .build();
            fileRepository.save(file2);

            File file3 = File.builder()
                    .path("README.md")
                    .content("test1")
                    .note(savedNote)
                    .build();
            fileRepository.save(file3);

            // when
            SimpleFileInfoResponse simpleFileInfoResponse = fileService.getSimpleFileInfoList(1L);

            // then
            assertEquals("src", simpleFileInfoResponse.children().first().name());
            assertEquals("README", simpleFileInfoResponse.children().last().name());
        }
    }
}