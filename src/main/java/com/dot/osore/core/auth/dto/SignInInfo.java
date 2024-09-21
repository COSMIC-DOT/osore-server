package com.dot.osore.core.auth.dto;

public record SignInInfo (
        Long id
) {

    public SignInInfo {
        validateId(id);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
    }
}
