package com.dot.osore.core.note.controller;

import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.auth.handler.Login;
import com.dot.osore.core.note.dto.NoteListResponse;
import com.dot.osore.core.note.dto.NoteRequest;
import com.dot.osore.core.note.service.NoteService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
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

    final private NoteService noteService;

    @GetMapping("/notes")
    public Response getNoteList(@Login SignInInfo signInInfo) {
        try {
            NoteListResponse response = new NoteListResponse(noteService.getNoteList(signInInfo.id()));
            return Response.success(response);
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PostMapping("/note")
    public Response saveNote(@RequestBody NoteRequest note, @Login SignInInfo signInInfo) {
        try {
            noteService.saveNote(signInInfo.id(), note);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @DeleteMapping("/note")
    public Response deleteNote(@RequestParam Long id, @Login SignInInfo signInInfo) {
        try {
            noteService.deleteNote(signInInfo.id(), id);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}