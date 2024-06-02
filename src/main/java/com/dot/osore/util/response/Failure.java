package com.dot.osore.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Failure implements Result {
    private String message;
}
