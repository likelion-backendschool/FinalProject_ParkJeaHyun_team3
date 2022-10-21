package com.ll.exam.final_w2.app.postkeyword.repository;


import com.ll.exam.final_w2.app.postkeyword.entity.PostKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostKeywordRepository extends JpaRepository<PostKeyword, Long>, PostKeywordRepositoryCustom {
    Optional<PostKeyword> findByContent(String keywordContent);
}
