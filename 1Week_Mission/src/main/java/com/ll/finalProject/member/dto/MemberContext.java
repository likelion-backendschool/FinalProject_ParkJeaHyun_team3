package com.ll.finalProject.member.dto;

import com.ll.finalProject.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class MemberContext extends User {
    private final Long id;
    private final String nickname;
    private final String email;

    private Map<String, Object> attributes;
    private String userNameAttributeName;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return super.getAuthorities().stream().collect(Collectors.toSet());
    }
}
