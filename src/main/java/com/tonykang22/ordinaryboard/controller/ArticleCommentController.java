package com.tonykang22.ordinaryboard.controller;

import com.tonykang22.ordinaryboard.dto.UserAccountDto;
import com.tonykang22.ordinaryboard.dto.request.ArticleCommentRequest;
import com.tonykang22.ordinaryboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService commentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        // TODO: 인증 정보 추가 예정
        commentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of("tony", "1234", "tony@gmail.com", "rony", "gogo")));
        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        commentService.deleteArticleComment(commentId);
        return "redirect:/articles/" + articleId;
    }
}
