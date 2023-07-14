package com.tonykang22.ordinaryboard.repository;

import com.tonykang22.ordinaryboard.config.JpaConfig;
import com.tonykang22.ordinaryboard.domain.Article;
import com.tonykang22.ordinaryboard.domain.ArticleComment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(value = JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                      @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
    @DisplayName("select 테스트")
    void givenTestData_whenSelecting_thenWorksFine() {
        // given

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles)
                .isNotNull();
    }

    @Test
    @DisplayName("insert 테스트")
    void givenTestData_whenInserting_thenWorksFine() {
        // given
        long previousCount = articleRepository.count();

        // when
        Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));

        // then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @Test
    @DisplayName("update 테스트")
    void givenTestData_whenUpdating_thenWorksFine() {
        // given
        Article article = articleRepository.findById(1L)
                .orElseThrow();
        String updateHashtag = "#springboot";
        article.setHashtag(updateHashtag);

        // when
        Article savedArticle = articleRepository.save(article);

        // then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updateHashtag);
    }

    @Test
    @DisplayName("delete 테스트")
    void givenTestData_whenDeleting_thenWorksFine() {
        // given
        Article article = articleRepository.findById(1L)
                .orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // when
        articleRepository.delete(article);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}