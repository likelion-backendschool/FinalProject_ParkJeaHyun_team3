package com.ll.finalProject.member.dto;

import com.ll.finalProject.base.dto.BaseDto;
import com.ll.finalProject.post.dto.PostDto;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class MemberDto extends BaseDto {
    public String userName;

    public String password;

    public String nickName;

    public String email;

    public int authLevel;

    public Set<PostDto> postDtos = new LinkedHashSet<>();
}
