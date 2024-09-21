package com.dot.osore.domain.note.controller;

import com.dot.osore.domain.auth.service.AuthService;
import com.dot.osore.domain.note.dto.NoteInfoResponse;
import com.dot.osore.domain.note.dto.NoteRequest;
import com.dot.osore.domain.note.dto.NoteListResponse;
import com.dot.osore.domain.note.service.NoteService;
import com.dot.osore.util.constant.ErrorCode;
import com.dot.osore.util.response.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {
    final private AuthService authService;
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

    @GetMapping("/notes")
    public Response getNotes(HttpServletRequest request) {
        try {
//            Cookie session = authService.getSession(List.of(request.getCookies()));
//            Long id = authService.getUserId(session);

            NoteListResponse response = new NoteListResponse();
            return Response.success(response);
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PostMapping("/note")
    public Response saveNote(HttpServletRequest request, @RequestBody NoteRequest note) {
        try {
//            Cookie session = authService.getSession(List.of(request.getCookies()));
//            Long id = authService.getUserId(session);

//            noteService.saveNote(id, note);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @DeleteMapping("/note")
    public Response deleteNote(HttpServletRequest request, @RequestParam Long id) {
        try {
            noteService.deleteById(id);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
