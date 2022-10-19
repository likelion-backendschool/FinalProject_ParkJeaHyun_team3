package com.ll.finalProject.member.service;

import com.ll.finalProject.member.dto.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "mailbot883@gmail.com";

    @Async("mailExecutor")
    public void welcomeMailSend(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject("회원가입을 축하합니다!");
        message.setText("원하는 글을 작성하세요!");

        mailSender.send(message);
    }

    @Async("mailExecutor")
    public void tempPasswordMailSend(String to, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject("임시 비밀번호 발급");
        message.setText(tempPassword);

        mailSender.send(message);
    }
}
