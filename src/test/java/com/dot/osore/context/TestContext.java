package com.dot.osore.context;

import com.dot.osore.domain.auth.service.AuthService;
import com.dot.osore.domain.member.repository.UserRepository;
import com.dot.osore.domain.member.service.UserService;
import com.dot.osore.domain.note.repository.NoteRepository;
import com.dot.osore.domain.note.service.NoteService;
import com.dot.osore.domain.note.service.RepoService;
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
    protected NoteRepository noteRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected RepoService repoService;

    @Autowired
    protected NoteService noteService;

    @AfterEach
    void tearDown() {
        databaseCleaner.clear();
    }
}
