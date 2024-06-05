package com.dot.osore.domain.note.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoteResponse {
    Long id;
    String title;
    String avatar;
    String repository;
    String description;
    int contributors;
    int stars;
    int forks;
}
