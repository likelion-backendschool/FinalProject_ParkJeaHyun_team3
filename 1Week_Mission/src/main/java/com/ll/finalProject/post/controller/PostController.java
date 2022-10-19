package com.ll.finalProject.post.controller;

import com.ll.finalProject.form.PostWriteForm;
import com.ll.finalProject.member.dto.MemberContext;
import com.ll.finalProject.post.dto.PostDto;
import com.ll.finalProject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/write")
    public String getWriteForm(PostWriteForm postWriteForm) {
        return "post/write";
    }

    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid PostWriteForm postWriteForm) {
        PostDto postDto = postService.write(memberContext.getUsername(), postWriteForm.getSubject(), postWriteForm.getContent());
        return "redirect:/post/%d".formatted(postDto.getId());
    }

    @GetMapping("/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        PostDto postDto = postService.findById(id);
        model.addAttribute("postDto", postDto);
        return "post/detail";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("postDtos", postService.getAllPostList());
        return "post/list";
    }
}
