package com.dot.osore.domain.note.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoteListResponse {
    List<NoteResponse> list;

    @Builder
    public NoteListResponse(List<NoteResponse> list) {
        this.list = list;
    }
}
