package com.dot.osore.core.member.service;

import com.dot.osore.core.member.entity.Member;
import com.dot.osore.core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    final private MemberRepository memberRepository;

    public Long save(String name, String avatar) {
        Member savedMember = memberRepository.save(new Member(name, avatar));
        return savedMember.getId();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name).orElse(null);
    }
}
