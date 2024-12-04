//package com.dot.osore.core.note.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.dot.osore.context.TestContext;
//import com.dot.osore.core.note.dto.RepoInfoResponse;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//class RepoServiceTest extends TestContext {
//
//    @Nested
//    class getGithubRepositoryInfo_메소드는 {
//
//        @Test
//        void 깃허브_저장소_URL을_받아_브랜치와_버전을_반환한다() throws Exception {
//            // given
//            String url = "https://github.com/woowa-techcamp-2024/Team7-ELEVEN";
//
//            // when
//            RepoInfoResponse repoInfoResponse = repoService.getGithubRepositoryInfo(url);
//
//            // then
//            assertThat(repoInfoResponse.branch()).isNotEmpty();
//            assertThat(repoInfoResponse.version()).isNotEmpty();
//        }
//
//        @Test
//        void 깃허브_저장소에_버전이_없으면_default_버전을_반환한다() throws Exception {
//            // given
//            String url = "https://github.com/minseok-oh/problem-solving";
//
//            // when
//            RepoInfoResponse repoInfoResponse = repoService.getGithubRepositoryInfo(url);
//
//            // then
//            assertThat(repoInfoResponse.version()).contains("default");
//        }
//    }
//}