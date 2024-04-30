package com.dot.osore.util.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Success<T> implements Result {
    private T data;
}
