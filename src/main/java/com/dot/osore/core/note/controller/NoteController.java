package com.dot.osore.core.note.controller;

import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.auth.handler.Login;
import com.dot.osore.core.note.dto.DetailNoteListResponse;
import com.dot.osore.core.note.dto.NoteRequest;
import com.dot.osore.core.note.dto.UpdateNoteRequest;
import com.dot.osore.core.note.service.NoteService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
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
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    final private NoteService noteService;

    @GetMapping
    public Response getNoteList(@Login SignInInfo signInInfo) {
        try {
            DetailNoteListResponse response = new DetailNoteListResponse(noteService.getNoteList(signInInfo.id()));
            return Response.success(response);
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping("/{noteId}")
    public Response getNote(@PathVariable Long noteId, @Login SignInInfo signInInfo) {
        try {
            return Response.success(noteService.getSimpleNoteResponse(noteId));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PutMapping("/{noteId}")
    public Response updateNoteTitle(@PathVariable Long noteId, @RequestBody UpdateNoteRequest request, @Login SignInInfo signInInfo) {
        try {
            noteService.updateNoteTitle(noteId, request.title());
            DetailNoteListResponse response = new DetailNoteListResponse(noteService.getNoteList(signInInfo.id()));
            return Response.success(response);
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PostMapping
    public Response saveNote(@RequestBody NoteRequest note, @Login SignInInfo signInInfo) {
        try {
            noteService.saveNote(signInInfo.id(), note);
            DetailNoteListResponse response = new DetailNoteListResponse(noteService.getNoteList(signInInfo.id()));
            return Response.success(response);
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @DeleteMapping("/{noteId}")
    public Response deleteNote(@PathVariable Long noteId, @Login SignInInfo signInInfo) {
        try {
            noteService.deleteNote(noteId);
            DetailNoteListResponse response = new DetailNoteListResponse(noteService.getNoteList(signInInfo.id()));
            return Response.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PostMapping("/{noteId}/exit")
    public Response exitNote(@PathVariable Long noteId, @Login SignInInfo signInInfo) {
        try {
            noteService.changeViewedAt(noteId);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
