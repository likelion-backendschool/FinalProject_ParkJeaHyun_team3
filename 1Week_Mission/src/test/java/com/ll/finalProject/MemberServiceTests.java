package com.ll.finalProject;

import com.ll.finalProject.member.dto.MemberDto;
import com.ll.finalProject.member.entity.Member;
import com.ll.finalProject.member.repository.MemberRepository;
import com.ll.finalProject.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("1명 회원 생성 테스트")
    public void t1() throws Exception {
        //given
        String username = "user1";
        String password = "1234";
        String email = "user1@test.com";

        //when
        List<Member> beforeMembers = memberRepository.findAll();
        MemberDto memberDto = memberService.register(username, password, email);
        List<Member> afterMembers = memberRepository.findAll();

        //then
        assertThat(afterMembers.size()).isEqualTo(beforeMembers.size() + 1);
        assertThat(memberDto.getEmail()).isEqualTo("user1@test.com");
        assertThat(memberDto.getNickName()).isEqualTo("none");
        assertThat(memberDto.getCreateDate().getMonth()).isEqualTo(LocalDateTime.now().getMonth());
        assertThat(memberDto.getCreateDate().getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());
    }

    @Test
    @DisplayName("1명 회원 생성 테스트(+nickName)")
    public void t2() throws Exception {
        //given
        String username = "user1";
        String password = "1234";
        String email = "user1@test.com";
        String nickname = "Hong";

        //when
        List<Member> beforeMembers = memberRepository.findAll();
        MemberDto memberDto = memberService.register(username, password, email, nickname);
        List<Member> afterMembers = memberRepository.findAll();

        //then
        assertThat(afterMembers.size()).isEqualTo(beforeMembers.size() + 1);
        assertThat(memberDto.getEmail()).isEqualTo("user1@test.com");
        assertThat(memberDto.getNickName()).isEqualTo("Hong");
        assertThat(memberDto.getCreateDate().getMonth()).isEqualTo(LocalDateTime.now().getMonth());
        assertThat(memberDto.getCreateDate().getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());
    }

    @Test
    @DisplayName("회원가입 후 로그인 테스트")
    public void t3() throws Exception {
        //given
        String username = "user1";
        String password = "1234";
        String email = "user1@test.com";
        memberService.register(username, password, email);

        //when
        mvc.perform(post("/member/login")
                        .param("username", "user1")
                        .param("password", "1234")
                        .with(csrf()))
        //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    public void t4() throws Exception {
        //given
        String username = "user1";
        String password = "1234";
        String email = "user1@test.com";
        MemberDto memberDto = memberService.register(username, password, email);

        //when
        String newEmail = "newUser1@test.com";
        String newNickname = "aaa";
        MemberDto newMemberDto = memberService.modify(username, newEmail, newNickname);

        //then
        assertThat(memberDto.getEmail()).isNotEqualTo(newMemberDto.getEmail());
        assertThat(memberDto.getNickName()).isNotEqualTo(newMemberDto.getNickName());
        assertThat(newMemberDto.getEmail()).isEqualTo(newEmail);
        assertThat(newMemberDto.getNickName()).isEqualTo(newNickname);
    }
}
