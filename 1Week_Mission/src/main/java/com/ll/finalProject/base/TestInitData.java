package com.ll.finalProject.base;

import com.ll.finalProject.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
public class TestInitData {
    @Bean
    CommandLineRunner init(MemberService memberService, PasswordEncoder passwordEncoder) {
        return args -> {
            for (int i = 1; i <= 4; i++) {
                memberService.register("user" + i, "1234", "user" + i + "@test.com");
            }
        };
    }
}
