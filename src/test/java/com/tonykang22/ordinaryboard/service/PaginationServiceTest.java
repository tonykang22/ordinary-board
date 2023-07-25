package com.tonykang22.ordinaryboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    private final PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.sut = paginationService;
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수 입력 시, 페이징 바 리스트 반환")
    @MethodSource
    @ParameterizedTest(name = "[{index}] Curr : {0}, Total: {1} => {2}")
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expected) {
        // given

        // when
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber, totalPages);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnPaginationBarNumbers() {
        return Stream.of(
                arguments(0, 20, List.of(0, 1, 2, 3, 4)),
                arguments(1, 20, List.of(0, 1, 2, 3, 4)),
                arguments(2, 20, List.of(0, 1, 2, 3, 4)),
                arguments(3, 20, List.of(1, 2, 3, 4, 5)),
                arguments(4, 20, List.of(2, 3, 4, 5, 6)),
                arguments(5, 20, List.of(3, 4, 5, 6, 7)),
                arguments(6, 20, List.of(4, 5, 6, 7, 8)),
                arguments(11, 20, List.of(9, 10, 11, 12, 13)),
                arguments(19, 20, List.of(17, 18, 19))
        );
    }

    @DisplayName("현재 설정된 페이지네이션 바의 길이 반환")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        // given

        // when
        int barLength = sut.getBarLength();

        // then
        assertThat(barLength).isEqualTo(5);
    }
}