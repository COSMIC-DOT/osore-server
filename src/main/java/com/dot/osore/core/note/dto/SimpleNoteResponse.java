package com.dot.osore.core.note.dto;

import static com.dot.osore.global.github.GithubParser.parseRepoName;

import com.dot.osore.core.note.entity.Note;

public record SimpleNoteResponse(
        String title,
        String repository,
        String branch,
        String version
) {

    public static SimpleNoteResponse from(Note note) throws Exception {
        String url = note.getUrl();
        String repository = parseRepoName(url);

        return new SimpleNoteResponse(
                note.getTitle(),
                repository,
                note.getBranch(),
                note.getVersion()
        );
    }
}
