package com.dot.osore.core.file.dto;

import com.dot.osore.core.file.entity.File;
import com.dot.osore.global.resolver.LanguageResolver;

public record DetailFileInfoResponse(
        String content,
        String language
) {

    public static DetailFileInfoResponse from(File file) {
        String part = file.getPath();
        String extension;
        if (part.contains(".")) {
            extension = part.substring(part.lastIndexOf(".") + 1);
        } else {
            extension = part.substring(part.lastIndexOf("/") + 1).toLowerCase();
        }

        return new DetailFileInfoResponse(
                file.getContent(),
                LanguageResolver.getLanguageByExtension(extension)
        );
    }
}
