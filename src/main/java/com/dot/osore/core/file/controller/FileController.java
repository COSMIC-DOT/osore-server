package com.dot.osore.core.file.controller;

import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.auth.handler.Login;
import com.dot.osore.core.file.service.FileService;
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
public class FileController {

    private final FileService fileService;

    @GetMapping("/files")
    public Response getFileList(@Login SignInInfo signInInfo, @RequestParam Long noteId) {
        try {
            return Response.success(fileService.getFileInfoList(noteId));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
