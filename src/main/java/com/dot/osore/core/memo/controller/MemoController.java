package com.dot.osore.core.memo.controller;

import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.auth.handler.Login;
import com.dot.osore.core.memo.dto.CreateMemoRequest;
import com.dot.osore.core.memo.dto.MemoListResponse;
import com.dot.osore.core.memo.dto.MemoResponse;
import com.dot.osore.core.memo.dto.UpdateMemoRequest;
import com.dot.osore.core.memo.service.MemoService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping
    public Response getMemoList(@RequestParam Long noteId, @Login SignInInfo signInInfo) {
        try {
            List<Long> memoList = memoService.getMemoList(noteId);
            return Response.success(new MemoListResponse(memoList));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping("/{memoId}")
    public Response getMemo(@PathVariable Long memoId, @Login SignInInfo signInInfo) {
        try {
            String content = memoService.getMemo(memoId);
            return Response.success(new MemoResponse(content));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PostMapping
    public Response saveMemo(@RequestBody CreateMemoRequest createMemoRequest, @Login SignInInfo signInInfo) {
        try {
            memoService.saveMemo(createMemoRequest.noteId(), createMemoRequest.order(), createMemoRequest.content());
            List<Long> memoList = memoService.getMemoList(createMemoRequest.noteId());
            return Response.success(new MemoListResponse(memoList));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PutMapping("/{memoId}")
    public Response updateMemo(@PathVariable Long memoId, @RequestBody UpdateMemoRequest updateMemoRequest,
                               @Login SignInInfo signInInfo) {
        try {
            memoService.updateMemo(memoId, updateMemoRequest.content());
            List<Long> memoList = memoService.getMemoList(updateMemoRequest.noteId());
            return Response.success(new MemoListResponse(memoList));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @DeleteMapping("/{memoId}")
    public Response deleteMemo(@PathVariable Long memoId, @RequestParam Long noteId,
                               @Login SignInInfo signInInfo) {
        try {
            memoService.deleteMemo(memoId);
            List<Long> memoList = memoService.getMemoList(noteId);
            return Response.success(new MemoListResponse(memoList));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
