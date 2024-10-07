package com.dot.osore.core.file.controller;

import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.auth.handler.Login;
import com.dot.osore.core.file.service.FileService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping
    public Response getFileList(@RequestParam Long noteId, @Login SignInInfo signInInfo) {
        try {
            return Response.success(fileService.getSimpleFileInfoList(noteId));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping("/{fileId}")
    public Response getFile(@PathVariable Long fileId, @Login SignInInfo signInInfo) {
        try {
            return Response.success(fileService.getDetailFileInfo(fileId));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
