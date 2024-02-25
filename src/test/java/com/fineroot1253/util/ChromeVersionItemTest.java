package com.fineroot1253.util;

import static com.fineroot1253.util.ExceptionMessage.CREATE_CHROME_VERSION_ITEM_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChromeVersionItemTest {
    @Test
    @DisplayName("create:[크롬 버전 단일 객체 생성]")
    void create(){
        assertThat(new ChromeVersionItem("123")).isInstanceOf(ChromeVersionItem.class);
    }
    
    @Test
    @DisplayName("should_integer_string_when_:[크롬 버전 단일 객체 생성시 정수가 아닌 문자열은 예외 던짐]")
    void should_integer_string_when_create(){
        assertThatThrownBy(()-> new ChromeVersionItem("hong")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(CREATE_CHROME_VERSION_ITEM_EXCEPTION);
    }

    @ParameterizedTest
    @DisplayName("isLatestEqThan:[현재 객체가 더 나중 버전이거나 최신버전인지 타 같은 타입 객체와 비교]")
    @CsvSource({"123,true","121, true","124, false"})
    void isLatestEqThan(final String inputVersion, final boolean expected){
        ChromeVersionItem chromeVersionItem = new ChromeVersionItem("123");
        assertThat(chromeVersionItem.isLatestEqThan(new ChromeVersionItem(inputVersion))).isEqualTo(expected);
    }
}
