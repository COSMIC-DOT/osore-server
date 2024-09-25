package com.dot.osore.core.file.dto;

import com.dot.osore.core.file.entity.File;

public record DetailFileInfoResponse(
        String content
) {

    public static DetailFileInfoResponse from(File file) {
        return new DetailFileInfoResponse(
                file.getContent()
        );
    }
}
