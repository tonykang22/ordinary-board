package com.tonykang22.ordinaryboard.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<String> findAllDistinctHashtags();

}
