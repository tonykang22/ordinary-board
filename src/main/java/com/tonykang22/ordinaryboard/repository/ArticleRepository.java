package com.tonykang22.ordinaryboard.repository;

import com.tonykang22.ordinaryboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
