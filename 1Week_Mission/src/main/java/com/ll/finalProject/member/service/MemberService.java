package com.ll.finalProject.member.service;

import com.ll.finalProject.base.Util;
import com.ll.finalProject.base.exception.DataNotFoundException;
import com.ll.finalProject.base.exception.EmailDuplicatedException;
import com.ll.finalProject.base.exception.NicknameDuplicatedException;
import com.ll.finalProject.member.dto.MailDto;
import com.ll.finalProject.member.dto.MemberDto;
import com.ll.finalProject.member.entity.Member;
import com.ll.finalProject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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
        return this.register(username, password, email, null);
    }

    public MemberDto register(String userName, String password, String email, String nickname) {
        Member member = Member.builder()
                .username(userName)
                .password(this.passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .build();

        this.memberRepository.save(member);

        this.mailService.welcomeMailSend(email);

        return this.modelMapper.map(member, MemberDto.class);
    }

    public MemberDto modify(String username, String email, String nickname, String password) {
        Optional<Member> _member = memberRepository.findByusername(username);

        if (_member.isEmpty()) {
            throw new DataNotFoundException("회원이 존재하지 않습니다.");
        }

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

    public MemberDto modifyPassword(String username, String oldPassword, String newPassword) {
        Optional<Member> _member = memberRepository.findByusername(username);

        if (_member.isEmpty()) {
            throw new DataNotFoundException("회원이 존재하지 않습니다.");
        }

        Member member = _member.get();
        if (passwordEncoder.matches(oldPassword, member.getPassword())) {
            member.modifyMemberPassword(passwordEncoder.encode(newPassword));
            memberRepository.save(member);
        } else {
            throw new BadCredentialsException("기존 비밀번호가 일치하지 않습니다.");
        }

        return modelMapper.map(member, MemberDto.class);
    }

    public MemberDto findUsername(String email) {
        Optional<Member> _member = memberRepository.findByEmail(email);
        if (_member.isPresent()) {
            Member member = _member.get();
            return modelMapper.map(member, MemberDto.class);
        } else {
            throw new DataNotFoundException("해당하는 이메일의 회원이 없습니다.");
        }
    }

    public void findPassword(String username, String email) {
        Optional<Member> _member = memberRepository.findByusername(username);
        if (_member.isEmpty()) {
            throw new DataNotFoundException("회원이 존재하지 않습니다.");
        } else {
            Member member = _member.get();
            if (!member.getEmail().equals(email)) {
                throw new RuntimeException("이메일이 일치하지 않습니다.");
            } else {
                String tempPassword = RandomStringUtils.randomAlphanumeric(10);
                member.modifyMemberPassword(passwordEncoder.encode(tempPassword));
                memberRepository.save(member);
                this.mailService.tempPasswordMailSend(email, tempPassword);
            }
        }
    }
}
