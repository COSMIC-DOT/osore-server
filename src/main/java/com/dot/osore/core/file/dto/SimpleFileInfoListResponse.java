package com.dot.osore.core.file.dto;

import java.util.List;

public record SimpleFileInfoListResponse(
        List<SimpleFileInfoResponse> fileInfo
) {
}
