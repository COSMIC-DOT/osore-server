package com.dot.osore.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Success<T> implements Result {
    private T data;
}
