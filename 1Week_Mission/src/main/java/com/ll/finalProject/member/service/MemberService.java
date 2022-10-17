package com.ll.finalProject.member.service;

import com.ll.finalProject.member.dto.MemberDto;
import com.ll.finalProject.member.entity.Member;
import com.ll.finalProject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public MemberDto register(String userName, String password, String email) {
        return this.register(userName, password, email, "none");
    }

    public MemberDto register(String userName, String password, String email, String nickName) {
        Member member = Member.builder()
                .userName(userName)
                .password(this.passwordEncoder.encode(password))
                .email(email)
                .nickName(nickName)
                .build();

        this.memberRepository.save(member);

        return this.modelMapper.map(member, MemberDto.class);
    }
}
