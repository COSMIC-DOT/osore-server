package com.dot.osore.context;

import com.dot.osore.domain.auth.service.AuthService;
import com.dot.osore.domain.member.repository.UserRepository;
import com.dot.osore.domain.member.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public abstract class TestContext {
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AuthService authService;

    @AfterEach
    void tearDown() {
        databaseCleaner.clear();
    }
}
