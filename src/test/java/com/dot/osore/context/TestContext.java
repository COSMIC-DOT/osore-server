package com.dot.osore.context;

import com.dot.osore.core.auth.service.AuthService;
import com.dot.osore.core.file.repository.FileRepository;
import com.dot.osore.core.member.repository.MemberRepository;
import com.dot.osore.core.member.service.MemberService;
import com.dot.osore.core.memo.repository.MemoRepository;
import com.dot.osore.core.memo.service.MemoService;
import com.dot.osore.core.note.repository.NoteRepository;
import com.dot.osore.core.note.service.NoteService;
import com.dot.osore.core.note.service.RepoService;
import com.dot.osore.core.file.service.FileService;
import com.dot.osore.global.github.GithubConnector;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public abstract class TestContext {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    protected GithubConnector githubConnector;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected NoteRepository noteRepository;

    @Autowired
    protected FileRepository fileRepository;

    @Autowired
    protected MemoRepository memoRepository;

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected RepoService repoService;

    @Autowired
    protected NoteService noteService;

    @Autowired
    protected FileService fileService;

    @Autowired
    protected MemoService memoService;

    @AfterEach
    void tearDown() {
        databaseCleaner.clear();
    }
}
