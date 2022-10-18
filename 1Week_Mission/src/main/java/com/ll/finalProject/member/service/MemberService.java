package com.ll.finalProject.member.service;

import com.ll.finalProject.base.Util;
import com.ll.finalProject.base.exception.EmailDuplicatedException;
import com.ll.finalProject.base.exception.NicknameDuplicatedException;
import com.ll.finalProject.member.dto.MailDto;
import com.ll.finalProject.member.dto.MemberDto;
import com.ll.finalProject.member.entity.Member;
import com.ll.finalProject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MailService mailService;

    public MemberDto register(String username, String password, String email) {
        return this.register(username, password, email, "none");
    }

    public MemberDto register(String userName, String password, String email, String nickname) {
        Member member = Member.builder()
                .username(userName)
                .password(this.passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .build();

        this.memberRepository.save(member);

        MailDto mailDto = Util.Mail.getWelcomeMailDto(member.getEmail());
        this.mailService.mailSend(mailDto);

        return this.modelMapper.map(member, MemberDto.class);
    }

    public MemberDto modify(String username, String email, String nickname, String password) {
        Optional<Member> _member = memberRepository.findByusername(username);
        Member member = _member.get();
        if (passwordEncoder.matches(password, member.getPassword())) {
            member.modifyMember(email, nickname);
            try {
                memberRepository.save(member);
            } catch (DataIntegrityViolationException e) {
                if (memberRepository.findByNickname(nickname).isPresent()) {
                    throw new NicknameDuplicatedException("이미 사용중인 닉네임 입니다.");
                } else {
                    throw new EmailDuplicatedException("이미 사용중인 email 입니다.");
                }
            }
        } else {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return modelMapper.map(member, MemberDto.class);
    }
}
