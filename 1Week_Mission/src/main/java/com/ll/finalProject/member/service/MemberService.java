package com.ll.finalProject.member.service;

import com.ll.finalProject.member.entity.Member;
import com.ll.finalProject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String userName, String password, String email) {
        Member member = this.create(userName, password, email, "none");

        return member;
    }

    public Member create(String userName, String password, String email, String nickName) {
        Member member = Member.builder()
                .userName(userName)
                .password(this.passwordEncoder.encode(password))
                .email(email)
                .nickName(nickName)
                .build();

        this.memberRepository.save(member);

        return member;
    }
}
