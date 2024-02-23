package com.fineroot1253.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileUtilsTest {
    @Test
    @DisplayName("InputStream과 파일 객체를 넘겨 해당 파일 객체에 파일 쓰기")
    void write() throws IOException {
        //given
        File file = new File("src/test/resources/test.txt");
        String hello = "hello file!";
        //when
        FileUtils.write(new ByteArrayInputStream(hello.getBytes(StandardCharsets.UTF_8)), file);
        //then
        assertThat(Files.exists(Path.of("src/test/resources/test.txt"))).isTrue();
        assertThat(new String(new FileInputStream(file).readAllBytes(), StandardCharsets.UTF_8)).hasToString("hello file!");
    }

    @Test
    @DisplayName("파일 쓰기시 올바른 경로의 파일이 아닐 경우 예외 던짐")
    void should_valid_path_when_write(){
        //given
        File file = new File("src/test/wrong_path/test.txt");
        String hello = "hello file!";
        assertThatThrownBy(()-> FileUtils.write(new ByteArrayInputStream(hello.getBytes(StandardCharsets.UTF_8)), file))
                .isInstanceOf(IOException.class);
    }

    @Test
    @DisplayName("url을 사용해 파일을 다운로드")
    void download() throws IOException {
        String path = "src/test/resources/downloads/150.png";
        FileUtils.download("https://via.placeholder.com/150", path);
        assertThat(Files.exists(Path.of(path))).isTrue();
    }

    @Test
    @DisplayName("UnzipHelper zip 파일 해제")
    void unzip() {
        FileUtils.unzip("src/test/resources/unzip/testZip.zip","src/test/resources/unzip/testZip");
        assertThat(Files.exists(Path.of("src/test/resources/unzip/testZip"))).isTrue();
        assertThat(Files.exists(Path.of("src/test/resources/unzip/testZip/test_img.HEIC"))).isTrue();
    }

    @Test
    @DisplayName("UnzipHelper zip 파일 해제시 덮어 쓰기 동작")
    void unzip_overwrite() throws IOException {
        FileUtils.unzip("src/test/resources/unzip/test_dir.zip","src/test/resources/unzip");
        FileUtils.unzip("src/test/resources/unzip/test_dir_2.zip","src/test/resources/unzip");
        File file = new File("src/test/resources/unzip/test_dir/test.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        assertThat(new String(fileInputStream.readAllBytes())).hasToString("nice to meet you!");
    }

    @AfterAll
    static void removeTestData() throws IOException {
        Files.walkFileTree(Path.of("src/test/resources/downloads/150.png"), new SimpleFileVisitor<>(){
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

        Files.walkFileTree(Path.of("src/test/resources/unzip/testZip"), new SimpleFileVisitor<>(){
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

        Files.walkFileTree(Path.of("src/test/resources/unzip/test_dir"), new SimpleFileVisitor<>(){
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
