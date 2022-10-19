package com.ll.finalProject.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.ll.finalProject.base.entity.BaseEntity;
import com.ll.finalProject.post.entity.Post;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private Set<Post> posts = new LinkedHashSet<>();

    public void modifyMember(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
        convertToAuthor();
    }

    public void modifyMemberPassword(String password) {
        this.password = password;
    }

    public void convertToAuthor() {
        this.authLevel = 2;
    }
}
