package com.dot.osore.core.memo.dto;

import java.util.List;

public record MemoListResponse(
        List<Long> memos
) {
}
