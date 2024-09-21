package com.dot.osore.core.note.controller;

import com.dot.osore.core.note.dto.RepoInfoResponse;
import com.dot.osore.core.note.service.RepoService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RepoController {
    private final RepoService repoService;

    @GetMapping("/repo")
    public Response getGithubRepositoryInfo(@RequestParam String url) {
        try {
            RepoInfoResponse repoInfoResponse = repoService.getGithubRepositoryInfo(url);
            return Response.success(repoInfoResponse);
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}

