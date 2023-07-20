package com.tonykang22.ordinaryboard.service;

import com.tonykang22.ordinaryboard.domain.Article;
import com.tonykang22.ordinaryboard.domain.ArticleComment;
import com.tonykang22.ordinaryboard.domain.UserAccount;
import com.tonykang22.ordinaryboard.dto.ArticleCommentDto;
import com.tonykang22.ordinaryboard.dto.UserAccountDto;
import com.tonykang22.ordinaryboard.repository.ArticleCommentRepository;
import com.tonykang22.ordinaryboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;

    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;

    @DisplayName("게시글 ID로 조회 시, 해당하는 댓글 리스트 반환")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // Given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticleId(articleId)).willReturn(List.of(expected));

        // When
        List<ArticleCommentDto> actual = sut.searchArticleComments(articleId);
        actual.forEach(System.out::println);

        // Then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticleId(articleId);
    }

    @DisplayName("댓글 정보 입력 시, 댓글 저장")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안함")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);

        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("댓글 정보 입력 시, 댓글 수정")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
        // Given
        String oldContent = "content";
        String updatedContent = "댓글";
        ArticleComment articleComment = createArticleComment(oldContent);
        ArticleCommentDto dto = createArticleCommentDto(updatedContent);
        given(articleCommentRepository.getReferenceById(dto.id())).willReturn(articleComment);

        // When
        sut.updateArticleComment(dto);

        // Then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 댓글 정보 수정 시, 경고 로그를 찍고 아무 것도 안함")
    @Test
    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleCommentRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticleComment(dto);

        // Then
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("댓글 ID 입력 시, 댓글 삭제")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // Given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentRepository).deleteById(articleCommentId);

        // When
        sut.deleteArticleComment(articleCommentId);

        // Then
        then(articleCommentRepository).should().deleteById(articleCommentId);
    }


    private ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L,
                1L,
                createUserAccountDto(),
                content,
                LocalDateTime.now(),
                "tony",
                LocalDateTime.now(),
                "tony"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "tony",
                "password",
                "tony@mail.com",
                "Rony",
                "This is memo",
                LocalDateTime.now(),
                "tony",
                LocalDateTime.now(),
                "tony"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.of(
                createUserAccount(),
                Article.of(createUserAccount(), "title", "content", "hashtag"),
                content
        );
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "tony",
                "password",
                "tony@email.com",
                "Rony",
                null
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

}