package com.ll.finalProject.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ll.finalProject.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    private int authLevel;
}
