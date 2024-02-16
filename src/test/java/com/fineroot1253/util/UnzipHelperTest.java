package com.fineroot1253.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnzipHelperTest {
    @Test
    @DisplayName("ZipInstallHelper zip 파일 해제")
    void unzip() {
        UnzipHelper.unzip("src/test/resources/testZip.zip","src/test/resources/testZip");
        assertThat(Files.exists(Path.of("src/test/resources/testZip"))).isTrue();
        assertThat(Files.exists(Path.of("src/test/resources/testZip/test_img.HEIC"))).isTrue();
    }

    @AfterAll
    static void removeTestData() throws IOException {
        Files.walkFileTree(Path.of("src/test/resources/testZip"), new SimpleFileVisitor<>(){
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
