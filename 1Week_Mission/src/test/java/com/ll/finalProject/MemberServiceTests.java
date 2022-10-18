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

import java.time.LocalDateTime;
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
        String userName = "jea5158";
        String password = "1234";
        String email = "jea5158@gmail.com";

        //when
        List<Member> beforeMembers = memberRepository.findAll();
        MemberDto memberDto = memberService.register(userName, password, email);
        List<Member> afterMembers = memberRepository.findAll();

        //then
        assertThat(afterMembers.size()).isEqualTo(beforeMembers.size() + 1);
        assertThat(memberDto.getEmail()).isEqualTo("jea5158@gmail.com");
        assertThat(memberDto.getNickName()).isEqualTo("none");
        assertThat(memberDto.getCreateDate().getMonth()).isEqualTo(LocalDateTime.now().getMonth());
        assertThat(memberDto.getCreateDate().getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());
    }

    @Test
    @DisplayName("1명 회원 생성 테스트(+nickName)")
    public void t2() throws Exception {
        //given
        String userName = "jea5158";
        String password = "1234";
        String email = "jea5158@gmail.com";
        String nickName = "Hong";

        //when
        List<Member> beforeMembers = memberRepository.findAll();
        MemberDto memberDto = memberService.register(userName, password, email, nickName);
        List<Member> afterMembers = memberRepository.findAll();

        //then
        assertThat(afterMembers.size()).isEqualTo(beforeMembers.size() + 1);
        assertThat(memberDto.getEmail()).isEqualTo("jea5158@gmail.com");
        assertThat(memberDto.getNickName()).isEqualTo("Hong");
        assertThat(memberDto.getCreateDate().getMonth()).isEqualTo(LocalDateTime.now().getMonth());
        assertThat(memberDto.getCreateDate().getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());
    }

}
