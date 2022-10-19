package com.ll.finalProject.base;

import com.ll.finalProject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("postDtos", postService.get100PostList());
        return "index";
    }
}
