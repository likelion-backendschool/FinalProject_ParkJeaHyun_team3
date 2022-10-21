package com.ll.finalProject.member.controller;

import com.ll.finalProject.base.exception.DataNotFoundException;
import com.ll.finalProject.base.exception.EmailDuplicatedException;
import com.ll.finalProject.base.exception.NicknameDuplicatedException;
import com.ll.finalProject.form.*;
import com.ll.finalProject.member.dto.MemberContext;
import com.ll.finalProject.member.dto.MemberDto;
import com.ll.finalProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final AuthenticationManager authenticationManager;

    @GetMapping("/join")
    public String getMemberRegisterForm(MemberRegisterForm memberRegisterForm) {
        return "member/signup_form";
    }

    @PostMapping("/join")
    public String memberRegister(@Valid MemberRegisterForm memberRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
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

    @GetMapping("/login")
    public String getLoginForm() {
        return "member/login_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String getModifyForm(@AuthenticationPrincipal MemberContext memberContext, MemberModifyForm memberModifyForm) {
        memberModifyForm.setEmail(memberContext.getEmail());
        memberModifyForm.setNickname(memberContext.getNickname());
        return "member/modifyMember_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, @Valid MemberModifyForm memberModifyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/modifyMember_form";
        }

        try {
            memberService.modify(memberContext.getUsername(), memberModifyForm.getEmail(), memberModifyForm.getNickname(), memberModifyForm.getPassword());
        } catch (EmailDuplicatedException e) {
            e.printStackTrace();
            bindingResult.reject("EmailDuplicated", e.getMessage());
            return "member/modify_form";
        } catch (NicknameDuplicatedException e) {
            e.printStackTrace();
            bindingResult.reject("NicknameDuplicated", e.getMessage());
            return "member/modify_form";
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            bindingResult.reject("PasswordInCorrect", e.getMessage());
            return "member/modify_form";
        }

        /* 변경된 세션 등록 */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(memberContext.getUsername(), memberModifyForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String getModifyPasswordForm(ModifyPasswordForm modifyPasswordForm) {
        return "member/modifyPassword_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal MemberContext memberContext, @Valid ModifyPasswordForm modifyPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/modifyPassword_form";
        }

        try {
            memberService.modifyPassword(memberContext.getUsername(), modifyPasswordForm.getOldPassword(), modifyPasswordForm.getPassword());
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            bindingResult.reject("PasswordInCorrect", e.getMessage());
            return "member/modifyPassword_form";
        }

        /* 변경된 세션 등록 */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(memberContext.getUsername(), modifyPasswordForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/findUsername")
    public String getFindUsernameForm(FindUsernameForm findUsernameForm) {
        return "member/findUsername_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findUsername")
    public String findUsername(Model model, @Valid FindUsernameForm findUsernameForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/findUsername_form";
        }

        try {
            MemberDto memberDto = memberService.findUsername(findUsernameForm.getEmail());
            model.addAttribute("memberDto", memberDto);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            bindingResult.reject("MemberNotFound", e.getMessage());
            return "member/findUsername_form";
        }

        return "member/showUsername";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/findPassword")
    public String getFindPasswordForm(FindPasswordForm findPasswordForm) {
        return "member/findPassword_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findPassword")
    public String findPassword(@Valid FindPasswordForm findPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/findPassword_form";
        }

        try {
            memberService.findPassword(findPasswordForm.getUsername(), findPasswordForm.getEmail());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            bindingResult.reject("MemberNotFound", e.getMessage());
            return "member/findPassword_form";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("findPasswordFailed", e.getMessage());
            return "member/findPassword_form";
        }

        return "redirect:/";
    }
}
