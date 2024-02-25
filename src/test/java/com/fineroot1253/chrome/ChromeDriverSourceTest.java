package com.fineroot1253.chrome;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChromeDriverSourceTest {
    @Test
    @DisplayName("경로와 버전을 넘겨받아 크롬드라이버 객체 생성")
    void create() {
        assertThat(new ChromeDriverSource("src/test/resources/oldChromeDriver", "121.0.6167.85"))
                .isInstanceOf(ChromeDriverSource.class);
    }

    @Test
    @DisplayName("크롬드라이버 업데이트 필요 여부 반환")
    void needUpdate() throws IOException {
        //given
        ChromeDriverSource chromeDriverSource = new ChromeDriverSource("src/test/resources/oldChromeDriver",
                "121.0.6167.85");
        //when
        boolean needUpdate = chromeDriverSource.needUpdate();
        //then
        assertThat(needUpdate).isTrue();
    }

    @Test
    @DisplayName("크롬드라이버 경로 반환")
    void getPath() {
        ChromeDriverSource chromeDriverSource = new ChromeDriverSource("src/test/resources/oldChromeDriver",
                "121.0.6167.85");
        assertThat(chromeDriverSource.getPath()).hasToString("src/test/resources/oldChromeDriver");
    }
}
