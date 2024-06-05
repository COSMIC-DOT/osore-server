package com.dot.osore.domain.note.dto;

import lombok.Data;

@Data
public class NoteRequest {
    private String url;
    private String title;
    private String branch;
    private String version;
}
