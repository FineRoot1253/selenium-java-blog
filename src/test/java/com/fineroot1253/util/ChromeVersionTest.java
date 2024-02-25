package com.fineroot1253.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChromeVersionTest {
    @Test
    @DisplayName("크롬 버전 객체 생성")
    void create(){
        assertThat(new ChromeVersion("121.0.6167.85")).isInstanceOf(ChromeVersion.class);
    }

    @ParameterizedTest
    @DisplayName("버전의 크기가 같거나 더 최신인지 같은 타입의 타 객체와 비교")
    @CsvSource({"121.1.6167.85,true", "120.1.6167.85,true","121.0.6167.85,true","121.1.6166.85,true","121.1.6167.84,true", "122.1.6167.84,false"})
    void isLatestEqThan(final String expectedVersion, final boolean expected){
        //given
        ChromeVersion actualVersion = new ChromeVersion("121.1.6167.85");
        //when
        boolean actual = actualVersion.isLatestVersionThan(new ChromeVersion(expectedVersion));
        //then
        assertThat(actual).isEqualTo(expected);
    }
}
