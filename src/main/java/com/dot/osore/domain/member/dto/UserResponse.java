package com.dot.osore.domain.member.dto;

import com.dot.osore.domain.member.entity.User;
import lombok.Builder;
import lombok.Data;

// TODO: User -> Member 변경 필요
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
