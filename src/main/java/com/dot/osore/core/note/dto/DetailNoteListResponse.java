package com.dot.osore.core.note.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetailNoteListResponse {
    List<DetailNoteResponse> list;

    @Builder
    public DetailNoteListResponse(List<DetailNoteResponse> list) {
        this.list = list;
    }
}
