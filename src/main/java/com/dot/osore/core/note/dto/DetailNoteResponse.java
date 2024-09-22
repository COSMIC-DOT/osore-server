package com.dot.osore.core.note.dto;

import com.dot.osore.core.note.entity.Note;

public record DetailNoteResponse(
        Long id,
        String title,
        String avatar,
        String repository,
        String description,
        Integer contributorsCount,
        Integer starsCount,
        Integer forksCount
) {

    public static DetailNoteResponse from(Note note) {
        return new DetailNoteResponse(
                note.getId(),
                note.getTitle(),
                note.getAvatar(),
                note.getUrl(),
                note.getDescription(),
                note.getContributorsCount(),
                note.getStarsCount(),
                note.getForksCount()
        );
    }
}
