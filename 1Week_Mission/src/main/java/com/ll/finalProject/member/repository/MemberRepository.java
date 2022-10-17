package com.ll.finalProject.member.repository;

import com.ll.finalProject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
