package com.dot.osore.core.memo.dto;

public record CreateMemoRequest(
        Long noteId,
        Long order,
        String content
) {
}
