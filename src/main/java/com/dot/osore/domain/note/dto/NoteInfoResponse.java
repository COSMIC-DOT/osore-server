package com.dot.osore.domain.note.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoteInfoResponse {
    private List<String> branch;
    private List<String> version;

    @Builder
    public NoteInfoResponse(List<String> branch, List<String> version) {
        this.branch = branch;
        this.version = version;
    }
}
