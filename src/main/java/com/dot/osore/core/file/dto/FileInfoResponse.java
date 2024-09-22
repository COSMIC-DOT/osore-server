package com.dot.osore.core.file.dto;

import java.util.List;

public record FileInfoResponse (
        String type,
        String name,
        String extension,
        List<FileInfoResponse> children
) {
}
