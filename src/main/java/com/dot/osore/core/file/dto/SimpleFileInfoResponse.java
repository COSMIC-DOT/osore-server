package com.dot.osore.core.file.dto;

import java.util.TreeSet;

public record SimpleFileInfoResponse(
        Long id,
        String type,
        String name,
        String extension,
        TreeSet<SimpleFileInfoResponse> children
) implements Comparable<SimpleFileInfoResponse> {

    public SimpleFileInfoResponse findChildByName(String name) {
        for (SimpleFileInfoResponse child : children) {
            if (child.name.equals(name) && child.type.equals("folder")) {
                return child;
            }
        }
        return null;
    }

    @Override
    public int compareTo(SimpleFileInfoResponse simpleFileInfoResponse) {
        if (this.type.equals("folder") && simpleFileInfoResponse.type.equals("file")) {
            return -1;
        }
        if (this.type.equals("file") && simpleFileInfoResponse.type.equals("folder")) {
            return 1;
        }
        return this.name.compareTo(simpleFileInfoResponse.name);
    }
}
