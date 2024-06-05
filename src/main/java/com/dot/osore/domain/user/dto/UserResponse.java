package com.dot.osore.domain.user.dto;

import com.dot.osore.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {
    private String name;
    private String avatar;

    @Builder
    public UserResponse(User user) {
        this.name = user.getName();
        this.avatar = user.getAvatar();
    }
}
