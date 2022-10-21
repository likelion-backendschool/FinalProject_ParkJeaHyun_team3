package com.ll.finalProject.post.controller;

import com.ll.finalProject.base.Util;
import com.ll.finalProject.base.exception.DataNotFoundException;
import com.ll.finalProject.base.exception.NoAuthorizationException;
import com.ll.finalProject.form.PostModifyForm;
import com.ll.finalProject.form.PostWriteForm;
import com.ll.finalProject.member.dto.MemberContext;
import com.ll.finalProject.post.dto.PostDto;
import com.ll.finalProject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("postDtos", postService.getAllPostList());
        return "post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String getWriteForm(PostWriteForm postWriteForm) {
        return "post/writePost_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid PostWriteForm postWriteForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/modify_form";
        }

        PostDto postDto;
        try {
            postDto = postService.write(memberContext.getUsername(), postWriteForm.getSubject(), postWriteForm.getContent());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return "redirect:/post/list?errorMsg=" + Util.url.encode(e.getMessage());
        }

        return "redirect:/post/%d".formatted(postDto.getId());
    }

    @GetMapping("/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        try {
            PostDto postDto = postService.findById(id);
            model.addAttribute("postDto", postDto);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return "redirect:/post/list?errorMsg=" + Util.url.encode(e.getMessage());
        }

        return "post/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String getModifyForm(Model model, PostModifyForm postModifyForm, @PathVariable Long id) {
        try {
            PostDto postDto = postService.findById(id);
            model.addAttribute("postId", id);
            postModifyForm.setSubject(postDto.getSubject());
            postModifyForm.setContent(postDto.getContent());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return "redirect:/post/list?errorMsg=" + Util.url.encode(e.getMessage());
        }

        return "post/modifyPost_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(Model model, @AuthenticationPrincipal MemberContext memberContext, @Valid PostModifyForm postModifyForm, @PathVariable Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/modify_form";
        }

        try {
            PostDto postDto = postService.modifyPost(memberContext.getId(), id, postModifyForm.getSubject(), postModifyForm.getContent());
            model.addAttribute("postDto", postDto);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return "redirect:/post/list?errorMsg=" + Util.url.encode(e.getMessage());
        } catch (NoAuthorizationException e) {
            e.printStackTrace();
            return "redirect:/post/" + id + "?errorMsg=" + Util.url.encode(e.getMessage());
        }

        return "redirect:/post/list";
    }
}
