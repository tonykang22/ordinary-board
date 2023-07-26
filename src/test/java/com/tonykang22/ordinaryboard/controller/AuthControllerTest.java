package com.tonykang22.ordinaryboard.controller;

import com.tonykang22.ordinaryboard.config.SecurityConfig;
import com.tonykang22.ordinaryboard.service.ArticleService;
import com.tonykang22.ordinaryboard.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 인증")
@Import(SecurityConfig.class)
@WebMvcTest
class AuthControllerTest {

    private final MockMvc mvc;

    @MockBean
    ArticleService articleService;
    @MockBean
    PaginationService paginationService;


    public AuthControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view]: GET - 게시글 리스트 (게시판) 페이지: 정상 호출")
    @Test
    void givenNothing_whenTryingToLogIn_thenReturnsLogInView() throws Exception {
        // given

        // when & then
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

}
