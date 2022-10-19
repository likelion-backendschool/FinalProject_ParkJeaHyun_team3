package com.ll.finalProject.post.dto;

import com.ll.finalProject.hashTag.dto.HashTagDto;
import com.ll.finalProject.member.dto.MemberDto;

import java.util.LinkedHashSet;
import java.util.Set;

public class PostDto {
    public String subject;

    public String content;

    public MemberDto memberDto;

    public Set<HashTagDto> hashTagDtos = new LinkedHashSet<>();
}
