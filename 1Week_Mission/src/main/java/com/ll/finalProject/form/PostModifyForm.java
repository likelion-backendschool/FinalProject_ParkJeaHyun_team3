package com.ll.finalProject.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PostModifyForm {
    @NotEmpty(message = "제목을 입력해주세요.")
    private String subject;

    @NotEmpty(message = "본문을 입력해주세요.")
    private String content;
}
