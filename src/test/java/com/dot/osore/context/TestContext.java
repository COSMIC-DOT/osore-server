package com.dot.osore.context;

import com.dot.osore.core.auth.service.AuthService;
import com.dot.osore.core.member.repository.MemberRepository;
import com.dot.osore.core.member.service.MemberService;
import com.dot.osore.core.note.repository.NoteRepository;
import com.dot.osore.core.note.service.NoteService;
import com.dot.osore.core.note.service.RepoService;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public abstract class TestContext {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected NoteRepository noteRepository;

    @Autowired
    protected MemberService memberService;

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
