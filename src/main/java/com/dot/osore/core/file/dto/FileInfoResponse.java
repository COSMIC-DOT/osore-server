package com.dot.osore.core.file.dto;

import java.util.TreeSet;

public record FileInfoResponse (
        String type,
        String name,
        String extension,
        TreeSet<FileInfoResponse> children
) implements Comparable<FileInfoResponse> {

    public FileInfoResponse findChildByName(String name) {
        for (FileInfoResponse child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    @Override
    public int compareTo(FileInfoResponse fileInfoResponse) {
        if (this.type.equals("folder") && fileInfoResponse.type.equals("file")) {
            return -1;
        }
        if (this.type.equals("file") && fileInfoResponse.type.equals("folder")) {
            return 1;
        }
        return this.name.compareTo(fileInfoResponse.name);
    }
}
