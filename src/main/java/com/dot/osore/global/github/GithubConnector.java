package com.dot.osore.global.github;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubConnector {

    @Value("${client.github.tokens}")
    private String concatedTokens;

    private List<String> tokens;
    private AtomicInteger currentTokenIndex;

    @PostConstruct
    public void init() {
        tokens = Arrays.asList(concatedTokens.split(","));
        currentTokenIndex = new AtomicInteger(0);
    }

    public GitHub getGithubInstance() throws Exception {
        String currentToken = tokens.get(currentTokenIndex.get());
        currentTokenIndex.updateAndGet(index -> (index + 1) % tokens.size());
        return new GitHubBuilder().withOAuthToken(currentToken).build();
    }

}
