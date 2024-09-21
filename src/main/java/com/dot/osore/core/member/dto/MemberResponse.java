package com.dot.osore.core.member.dto;

import com.dot.osore.core.member.entity.Member;

public record MemberResponse (
        String name,
        String avatar
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getName(),
                member.getAvatar()
        );
    }
}
