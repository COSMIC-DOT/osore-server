package com.dot.osore.core.memo.controller;

import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.auth.handler.Login;
import com.dot.osore.core.memo.dto.CreateMemoRequest;
import com.dot.osore.core.memo.dto.MemoResponse;
import com.dot.osore.core.memo.dto.UpdateMemoRequest;
import com.dot.osore.core.memo.service.MemoService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/memo")
    public Response getMemo(@RequestParam Long memoId, @Login SignInInfo signInInfo) {
        try {
            String content = memoService.getMemo(memoId);
            return Response.success(new MemoResponse(content));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PostMapping("/memo")
    public Response saveMemo(@RequestBody CreateMemoRequest createMemoRequest, @Login SignInInfo signInInfo) {
        try {
            memoService.saveMemo(createMemoRequest.noteId(), createMemoRequest.order(), createMemoRequest.content());
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PutMapping("/memo")
    public Response updateMemo(@RequestBody UpdateMemoRequest updateMemoRequest, @Login SignInInfo signInInfo) {
        try {
            memoService.updateMemo(updateMemoRequest.memoId(), updateMemoRequest.content());
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @DeleteMapping("/memo")
    public Response deleteMemo(@RequestParam Long memoId, @Login SignInInfo signInInfo) {
        try {
            memoService.deleteMemo(memoId);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
