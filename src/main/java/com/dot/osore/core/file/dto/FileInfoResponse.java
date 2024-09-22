package com.dot.osore.core.file.dto;

import java.util.ArrayList;
import java.util.List;

public record FileInfoResponse (
        String type,
        String name,
        String extension,
        List<FileInfoResponse> children
) {

    public FileInfoResponse findChildByName(String name) {
        for (FileInfoResponse child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }
}
