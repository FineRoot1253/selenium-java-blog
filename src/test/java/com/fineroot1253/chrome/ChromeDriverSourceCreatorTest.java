package com.fineroot1253.chrome;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChromeDriverSourceCreatorTest {
    @Test
    @DisplayName("경로를 받아 크롬 드라이버 객체 생성")
    void create() throws IOException {
        //given
        ChromeDriverSourceCreator chromeDriverSourceCreator = new ChromeDriverSourceCreator();
        //when
        ChromeDriverSource chromeDriverSource = chromeDriverSourceCreator.create(
                "src/test/resources/chromeDriver/oldChromeDriver/chromedriver");
        //then
        assertThat(chromeDriverSource).isInstanceOf(ChromeDriverSource.class);
    }

    @Test
    @DisplayName("잘못된 경로를 받아 크롬 드라이버 객체 생성시 예외 던짐")
    void should_right_path_when_create() throws IOException {
        //given
        ChromeDriverSourceCreator chromeDriverSourceCreator = new ChromeDriverSourceCreator();
        //when
        String wrongPath = "src/test/resources/chromeDriver/chromedriver";
        //then
        assertThatThrownBy(()->chromeDriverSourceCreator.create(
                wrongPath))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("No such file or directory");
    }
}
