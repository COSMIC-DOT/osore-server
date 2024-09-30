package com.dot.osore.core.note.dto;

import static com.dot.osore.global.github.GithubParser.parseRepoName;

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
        try {
            return new DetailNoteResponse(
                    note.getId(),
                    note.getTitle(),
                    note.getAvatar(),
                    parseRepoName(note.getUrl()),
                    note.getDescription(),
                    note.getContributorsCount(),
                    note.getStarsCount(),
                    note.getForksCount()
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 URL 형식입니다.");
        }
    }
}
