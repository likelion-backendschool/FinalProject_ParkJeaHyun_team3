package com.ll.finalProject.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ModifyPasswordForm {
    @NotEmpty(message = "이전 비밀번호를 입력해주세요")
    private String oldPassword;

    @NotEmpty(message = "새 비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "새 비밀번호 확인을 입력해주세요")
    private String passwordConfirm;
}
