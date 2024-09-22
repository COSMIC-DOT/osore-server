package com.dot.osore.global.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GithubParserTest {

    @Nested
    class parseRepoName_메소드는 {

        @Test
        void 유효한_url의_리포지토리_이름을_추출한다() throws Exception {
            // given
            String url = "https://github.com/COSMIC-DOT/osore-server";

            // when
            String repoName = GithubParser.parseRepoName(url);

            // then
            assertEquals("COSMIC-DOT/osore-server", repoName);
        }

        @Test
        void 유효하지_않은_url을_입력받으면_예외를_던진다() {
            // given
            String url = "";

            // expect
            assertThrows(Exception.class, () -> GithubParser.parseRepoName(url));
        }
    }
}