package com.dot.osore.core.chat.dto;

public record SendChatRequest(
        Long noteId,
        Long roomId,
        String chat
) {
}
