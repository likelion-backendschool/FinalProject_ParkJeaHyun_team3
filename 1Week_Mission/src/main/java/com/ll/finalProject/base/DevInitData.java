package com.ll.finalProject.base;

import com.ll.finalProject.member.service.MemberService;
import com.ll.finalProject.post.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevInitData {
    @Bean
    CommandLineRunner init(MemberService memberService, PostService postService, PasswordEncoder passwordEncoder) {
        return args -> {
            for (int i = 1; i <= 4; i++) {
                memberService.register("user" + i, "1234", "user" + i + "@test.com");
            }

            for (int i = 1; i <= 4; i++) {
                postService.write("user" + i, "test" + i, "test" + i);
            }
        };
    }
}
