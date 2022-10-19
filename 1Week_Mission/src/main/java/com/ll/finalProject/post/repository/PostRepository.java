package com.ll.finalProject.post.repository;

import com.ll.finalProject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
