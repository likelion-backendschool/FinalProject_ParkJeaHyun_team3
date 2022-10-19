package com.ll.finalProject.member;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    AUTHOR("ROLE_AUTHOR");

    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}
