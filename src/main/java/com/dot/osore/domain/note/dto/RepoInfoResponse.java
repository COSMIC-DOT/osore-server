package com.dot.osore.domain.note.dto;

import java.util.List;

public record RepoInfoResponse (
    List<String> branch,
    List<String> version
) {

    public RepoInfoResponse(List<String> branch, List<String> version) {
        this.branch = branch;
        this.version = version;

        if (this.version.isEmpty()) {
            this.version.add("default");
        }
    }
}
