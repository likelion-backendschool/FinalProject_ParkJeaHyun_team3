package com.ll.finalProject.base;

import com.ll.finalProject.member.dto.MailDto;

public class Util {
    public static class Mail {
        public static MailDto getWelcomeMailDto(String email) {
            MailDto mailDto = new MailDto();
            mailDto.setAddress(email);
            mailDto.setTitle("회원가입을 환영합니다!");
            mailDto.setMessage("마켓앱을 통해 멋진 글을 작성해보세요!");

            return mailDto;
        }
    }
}
