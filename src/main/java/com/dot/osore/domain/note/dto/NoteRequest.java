package com.dot.osore.domain.note.dto;

public record NoteRequest(
        String url,
        String title,
        String branch,
        String version
) {
}
