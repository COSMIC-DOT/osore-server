package com.dot.osore.core.note.dto;

public record NoteRequest(
        String url,
        String title,
        String branch,
        String version
) {
}
