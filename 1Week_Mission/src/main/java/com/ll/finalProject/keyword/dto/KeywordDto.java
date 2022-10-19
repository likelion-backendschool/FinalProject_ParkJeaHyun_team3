package com.ll.finalProject.keyword.dto;

import com.ll.finalProject.base.dto.BaseDto;
import com.ll.finalProject.hashTag.dto.HashTagDto;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class KeywordDto extends BaseDto {
    public String content;

    public Set<HashTagDto> hashTagDtos = new LinkedHashSet<>();
}
