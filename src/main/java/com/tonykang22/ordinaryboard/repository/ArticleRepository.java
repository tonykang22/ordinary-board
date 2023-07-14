package com.tonykang22.ordinaryboard.repository;

import com.tonykang22.ordinaryboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
