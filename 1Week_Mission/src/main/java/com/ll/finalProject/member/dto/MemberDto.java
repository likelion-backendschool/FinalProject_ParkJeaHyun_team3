package com.ll.finalProject.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {
    public Long id;

    public LocalDateTime createTime;

    public LocalDateTime modifyDate;

    public String userName;

    public String password;

    public String nickName;

    public String email;

    public int authLevel;
}
