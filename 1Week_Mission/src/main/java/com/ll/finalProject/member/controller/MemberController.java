package com.ll.finalProject.member.controller;

import com.ll.finalProject.member.MemberRegisterForm;
import com.ll.finalProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String getMemberRegisterForm(MemberRegisterForm memberRegisterForm) {
        return "member/signup_form";
    }

    @PostMapping("/join")
    public String memberRegister(@Valid MemberRegisterForm memberRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signup_form";
        }

        if (!memberRegisterForm.getPassword1().equals(memberRegisterForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "member/signup_form";
        }

        try {
            memberService.register(memberRegisterForm.getUsername(), memberRegisterForm.getPassword1(), memberRegisterForm.getEmail());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "member/signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "member/signup_form";
        }

        return "redirect:/member/welcome";
    }

    @GetMapping("/welcome")
    public String getWelcomePage() {
        return "member/welcome";
    }
}
