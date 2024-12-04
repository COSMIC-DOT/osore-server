package com.dot.osore.core.chat.dto;

public record EmbeddingRequest(
        Long room_id,
        String repository_url,
        Long note_id
) {
}
