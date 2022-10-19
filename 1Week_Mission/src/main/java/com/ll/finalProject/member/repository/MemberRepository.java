package com.ll.finalProject.member.repository;

import java.util.Optional;

import com.ll.finalProject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByusername(String username);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);
}
