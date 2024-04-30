package com.dot.osore.util.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Failure implements Result {
    private String message;
}
