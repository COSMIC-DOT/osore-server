package com.dot.osore.core.chat.dto;

public record SendChatRequest(
        Long noteId,
        Long roomId,
        String chat
) {
    public SendChatRequest {
        validateChat(chat);
    }

    private void validateChat(String chat) {
        if (chat == null || chat.isBlank()) {
            throw new IllegalArgumentException("채팅은 비어있을 수 없습니다");
        }
    }
}
