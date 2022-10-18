package com.ll.finalProject.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDto {
    public Long id;

    public LocalDateTime createDate;

    public LocalDateTime modifyDate;
}
