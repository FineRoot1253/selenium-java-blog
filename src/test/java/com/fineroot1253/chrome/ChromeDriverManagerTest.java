package com.fineroot1253.chrome;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fineroot1253.driver.DriverSource;
import com.fineroot1253.factory.DriverSourceFactoryBean;
import com.fineroot1253.util.FileUtils;
import com.fineroot1253.util.OsType;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

class ChromeDriverManagerTest {

    @BeforeAll
    @EnabledOnOs(OS.MAC)
    static void setUp() {
        FileUtils.unzip("src/test/resources/chromedriver-mac-x64.zip","src/test/resources/chromeDriver/old_version");
        OsType.from("mac","x86_64").grantPermission("src/test/resources/chromeDriver/old_version/chromedriver-mac-x64/chromedriver");
    }

    @Test
    @DisplayName("크롬드라이버 매니저 객체 생성")
    @EnabledOnOs(OS.MAC)
    void create() throws IOException {
        assertThat(new ChromeDriverManager("src/test/resources/chromeDriver/old_version/chromedriver-mac-x64/chromedriver")).isInstanceOf(ChromeDriverManager.class);
    }

    @Test
    @DisplayName("크롬드라이버 매니저 객체 생성시 엉뚱한 경로인 경우 퍼미션 예외 발생")
    void should_usable_path_when_create(){
        assertThatThrownBy(()->{
            new ChromeDriverManager("src/test/resources");
        }).isInstanceOf(IOException.class)
                .hasMessageContaining("Permission denied");
    }

    @Test
    @DisplayName("현재 버전보다 더 높은 버전이 존재하는 경우 기존 경로의 크롬드라이버를 최신 버전으로 교체")
    @EnabledOnOs(OS.MAC)
    void updateToLatestVersion() throws IOException {
        ChromeDriverManager chromeDriverManager = new ChromeDriverManager(
                "src/test/resources/chromeDriver/old_version/chromedriver-mac-x64/chromedriver");
        chromeDriverManager.updateToLatestVersion(OsType.from("mac", "x86_64"));
        DriverSource driverSource = DriverSourceFactoryBean.createCreator()
                .create("src/test/resources/chromeDriver/old_version/chromedriver-mac-x64/chromedriver");
        assertThat(driverSource.needUpdate()).isFalse();
    }

    @AfterAll
    @EnabledOnOs(OS.MAC)
    static void remove_old_version() throws IOException {
        Files.walkFileTree(Path.of("src/test/resources/chromeDriver/old_version"), new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
