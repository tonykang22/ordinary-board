package com.tonykang22.ordinaryboard.repository;

import com.tonykang22.ordinaryboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
