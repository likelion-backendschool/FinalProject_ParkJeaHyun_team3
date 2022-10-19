package com.ll.finalProject.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class FindPasswordForm {
    @NotEmpty(message = "아이디를 입력해주세요")
    private String username;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요")
    private String email;
}
