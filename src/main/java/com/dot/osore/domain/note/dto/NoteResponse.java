package com.dot.osore.domain.note.dto;

import com.dot.osore.domain.note.entity.Note;

public record NoteResponse(
        Long id,
        String title,
        String avatar,
        String repository,
        String description,
        Integer contributorsCount,
        Integer starsCount,
        Integer forksCount
) {

    public static NoteResponse createNoteResponse(Note note) {
        return new NoteResponse(
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
