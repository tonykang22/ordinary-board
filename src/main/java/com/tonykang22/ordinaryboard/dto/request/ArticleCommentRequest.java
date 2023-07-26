package com.tonykang22.ordinaryboard.dto.request;

import com.tonykang22.ordinaryboard.dto.ArticleCommentDto;
import com.tonykang22.ordinaryboard.dto.UserAccountDto;

public record ArticleCommentRequest(
        Long articleId,
        String content
) {
    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}
