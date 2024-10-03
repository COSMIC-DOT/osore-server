package com.dot.osore.core.memo.dto;

public record UpdateMemoRequest(
        Long noteId,
        String content
) {
}
