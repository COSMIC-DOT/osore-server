package com.dot.osore.util.response;

import com.dot.osore.util.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response {
    private Boolean success;
    private Integer code;
    private Result result;

    public static Response success() {
        return new Response(true, 0, null);
    }

    public static <T> Response success(T data) {
        return new Response(true, 0, new Success<>(data));
    }

    public static Response failure(ErrorCode errorCode) {
        return new Response(false, errorCode.getCode(), new Failure(errorCode.getMessage()));
    }
}
