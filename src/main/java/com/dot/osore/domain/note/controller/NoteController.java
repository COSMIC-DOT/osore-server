package com.dot.osore.domain.note.controller;

import com.dot.osore.domain.note.dto.NoteInfoResponse;
import com.dot.osore.domain.note.service.NoteService;
import com.dot.osore.util.constant.ErrorCode;
import com.dot.osore.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {
    final private NoteService noteService;

    @GetMapping("/note")
    public Response getGithubLinkInfo(@RequestParam String url) {
        try {
            NoteInfoResponse noteInfoResponse = noteService.getNoteInfo(url);
            return Response.success(noteInfoResponse);
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
