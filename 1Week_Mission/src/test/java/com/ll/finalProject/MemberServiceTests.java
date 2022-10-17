package com.ll.finalProject;

import com.ll.finalProject.member.dto.MemberDto;
import com.ll.finalProject.member.entity.Member;
import com.ll.finalProject.member.repository.MemberRepository;
import com.ll.finalProject.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceTests {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("1명 회원 생성 테스트")
    public void t1() throws Exception {
        //given
        String userName = "홍길동";
        String password = "1234";
        String email = "test@email.com";

        //when
        List<Member> beforeMembers = memberRepository.findAll();
        MemberDto memberDto = memberService.create(userName, password, email);
        List<Member> afterMembers = memberRepository.findAll();

        //then
        assertThat(afterMembers.size()).isEqualTo(beforeMembers.size() + 1);
        assertThat(memberDto.getEmail()).isEqualTo("test@email.com");
        assertThat(memberDto.getNickName()).isEqualTo("none");
    }

    @Test
    @DisplayName("1명 회원 생성 테스트(+nickName)")
    public void t2() throws Exception {
        //given
        String userName = "홍길동";
        String password = "1234";
        String email = "test@email.com";
        String nickName = "Hong";

        //when
        List<Member> beforeMembers = memberRepository.findAll();
        MemberDto memberDto = memberService.create(userName, password, email, nickName);
        List<Member> afterMembers = memberRepository.findAll();

        //then
        assertThat(afterMembers.size()).isEqualTo(beforeMembers.size() + 1);
        assertThat(memberDto.getEmail()).isEqualTo("test@email.com");
        assertThat(memberDto.getNickName()).isEqualTo("Hong");
    }

}
