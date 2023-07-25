package com.tonykang22.ordinaryboard.controller;

import com.tonykang22.ordinaryboard.domain.type.SearchType;
import com.tonykang22.ordinaryboard.dto.ArticleDto;
import com.tonykang22.ordinaryboard.dto.response.ArticleResponse;
import com.tonykang22.ordinaryboard.dto.response.ArticleWithCommentsResponse;
import com.tonykang22.ordinaryboard.service.ArticleService;
import com.tonykang22.ordinaryboard.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String articles(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentResponse());
        return "articles/detail";
    }

    @GetMapping("/search") public @ResponseBody Page<ArticleDto> searchArticles(SearchType type, String searchKeyword, @PageableDefault Pageable pageable) {
        return articleService.searchArticles(type, searchKeyword, pageable);
    }
}
