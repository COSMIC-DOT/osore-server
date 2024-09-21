package com.dot.osore.core.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dot.osore.context.TestContext;
import com.dot.osore.core.auth.dto.SignInInfo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AuthServiceTest extends TestContext {

    @Nested
    class signIn_메소드는 {

        @Test
        void 사용자_이름을_받아_사용자_정보를_저장하고_반환한다() {
            // given
            String name = "test";

            // when
            SignInInfo signInInfo = authService.signIn(name);

            // then
            assertThat(signInInfo.id()).isEqualTo(1L);
        }

        @Test
        void 같은_사용자_이름을_받으면_이미_존재하는_사용자의_정보를_반환한다() {
            // given
            String name = "test";
            authService.signIn(name);

            // when
            SignInInfo signInInfo = authService.signIn(name);

            // then
            assertThat(signInInfo.id()).isEqualTo(1L);
        }
    }
}