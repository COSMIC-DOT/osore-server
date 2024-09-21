package com.dot.osore.core.member.controller;

import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.auth.handler.Login;
import com.dot.osore.core.member.dto.MemberResponse;
import com.dot.osore.core.member.entity.Member;
import com.dot.osore.core.member.service.MemberService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    final private MemberService memberService;

    @GetMapping("/member")
    public Response getUserInfo(@Login SignInInfo signInInfo) {
        try {
            Member member = memberService.findById(signInInfo.id());
            return Response.success(MemberResponse.from(member));
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
