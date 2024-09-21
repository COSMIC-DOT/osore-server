package com.dot.osore.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND_EXCEPTION(1, "사용자가 존재하지 않습니다.");

    private final Integer code;
    private final String message;
}