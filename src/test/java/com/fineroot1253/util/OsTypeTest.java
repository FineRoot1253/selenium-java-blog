package com.fineroot1253.util;

import static com.fineroot1253.util.ExceptionMessage.CREATE_CHROME_DRIVER_MANAGER_EXCEPTION;
import static com.fineroot1253.util.ExceptionMessage.CREATE_OS_TYPE_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

class OsTypeTest {

    @BeforeAll
    @DisplayName("권한 할당 테스트용 폴더 생성")
    static void setUp() throws IOException {
        FileUtils.unzip("src/test/resources/chromedriver-mac-x64.zip","src/test/resources/test_dir");
    }

    @AfterAll
    @DisplayName("권한 할당 테스트 환경 해체")
    static void tearDown() throws IOException {
        Files.walkFileTree(Path.of("src/test/resources/test_dir"), new SimpleFileVisitor<>(){
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

    @ParameterizedTest
    @DisplayName("static 함수를 통해 OS 이름과 아키텍처 이름을 넘겨받아 OsType 생성")
    @CsvSource({"linux,x86_64,LINUX_AMD64","mac,x86_64,MAC_AMD64","mac,arm64,MAC_ARM64","win,x86,WIN_AMD32","win,x86_64,WIN_AMD64"})
    void from(final String osName, final String archName, final OsType expected){
        assertThat(OsType.from(osName, archName)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("static 함수를 통해 지원하지 않는 OS 이름과 아키텍처 이름을 넘겨받아 OsType 생성시 예외 던짐")
    @CsvSource({"linux,x86","mac,x86","win,arm64"})
    void should_valid_osName_or_archName_when_from(final String osName, final String archName){
        assertThatThrownBy(()->OsType.from(osName, archName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(CREATE_OS_TYPE_EXCEPTION);
    }

    @ParameterizedTest
    @DisplayName("버전을 입력하면 타입과 맞는 URL을 반환")
    @CsvFileSource(resources = "/osType_replaceURL_test.csv",numLinesToSkip = 1, delimiter = '|')
    void replaceURL(final String osName, final String archName, final String expected){
        assertThat(OsType.from(osName, archName).replaceURL("121.0.6167.184")).hasToString(expected);
    }

    @Test
    @DisplayName("경로를 입력하면 해당 경로에 소유자는 읽기, 쓰기, 실행 권한과 다른 유저는 쓰기, 실행 권한 부여")
    @EnabledOnOs(OS.MAC)
    void grantPermission() throws IOException {
        String path = "src/test/resources/test_dir/chromedriver-mac-x64/chromedriver";
        OsType.from("mac", "x86_64").grantPermission(path);
        Process process = new ProcessBuilder(path, "--version").start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String versionString = bufferedReader.readLine();
        assertThat(versionString).startsWith("ChromeDriver");
    }

    @Test
    @DisplayName("올바르지 않은 경로를 입력하면 예외 던짐")
    void should_right_path_when_grantPermission(){
        String path = "src/test/resources/test_dir/chromedriver-mac";
        assertThatThrownBy(()->OsType.from("mac", "x86_64").grantPermission(path))
                .isInstanceOf(IllegalStateException.class);
    }
}
