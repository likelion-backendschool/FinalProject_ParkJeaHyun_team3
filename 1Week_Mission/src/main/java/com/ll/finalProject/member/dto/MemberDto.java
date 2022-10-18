package com.ll.finalProject.member.dto;

import com.ll.finalProject.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto extends BaseDto {
    public String userName;

    public String password;

    public String nickName;

    public String email;

    public int authLevel;
}
