package com.ll.finalProject.post.dto;

import com.ll.finalProject.base.dto.BaseDto;
import com.ll.finalProject.hashTag.dto.HashTagDto;
import com.ll.finalProject.member.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class PostDto extends BaseDto {
    public String subject;

    public String content;

    public MemberDto memberDto;

    public Set<HashTagDto> hashTagDtos = new LinkedHashSet<>();
}
