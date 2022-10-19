package com.ll.finalProject.hashTag.dto;

import com.ll.finalProject.base.dto.BaseDto;
import com.ll.finalProject.keyword.dto.KeywordDto;
import com.ll.finalProject.post.dto.PostDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HashTagDto extends BaseDto {
    public PostDto postDto;

    public KeywordDto keywordDto;
}
