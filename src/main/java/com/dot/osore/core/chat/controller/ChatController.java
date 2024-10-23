package com.dot.osore.core.chat.controller;

import com.dot.osore.core.chat.dto.CreateChattingRoomRequest;
import com.dot.osore.core.chat.dto.CreateChattingRoomResponse;
import com.dot.osore.core.chat.service.ChatService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat-room")
    public Response createChatRoom(@RequestBody CreateChattingRoomRequest request) {
        try {
            Long noteId = request.noteId();
            Long chatRoomId = chatService.createChatRoom(noteId);
            return Response.success(
                    new CreateChattingRoomResponse(chatRoomId, chatService.getChattingRoomList(noteId)));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
