package com.fineroot1253.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.fineroot1253.factory.AclEntryFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class PermissionTypeTest {

    @ParameterizedTest
    @DisplayName("OS 타입을 문자열로 넘겨 받아 파싱해 객체생성")
    @ValueSource(strings = {"nix", "nux", "aix", "linux", "free", "mac", "hp-ux", "sun os", "sunos", "solaris"})
    void from_POSIX(final String osName) {
        assertThat(PermissionType.from(osName)).isEqualTo(PermissionType.POSIX);
    }

    @ParameterizedTest
    @DisplayName("OS 이름 문자열을 파싱해 객체생성")
    @ValueSource(strings = {"win", "windows"})
    void from_WIN(final String osName) {
        assertThat(PermissionType.from(osName)).isEqualTo(PermissionType.WIN);
    }

    @ParameterizedTest
    @DisplayName("OS 이름 문자열을 파싱해 객체생성")
    @ValueSource(strings = {"ios", "android"})
    void from_OTHER(final String osName) {
        assertThat(PermissionType.from(osName)).isEqualTo(PermissionType.OTHER);
    }

    @ParameterizedTest
    @DisplayName("권한 타입에 따라 다른 방식의 디렉터리에 권한을 부여")
    @EnumSource(PermissionType.class)
    @EnabledOnOs(OS.LINUX)
    void grantOnDirectory_on_LINUX(final PermissionType permissionType) {
        permissionType.grantPermission("./resource");
        assertThat(permissionType).isEqualTo(PermissionType.OTHER);
    }

    @ParameterizedTest
    @DisplayName("권한 타입에 따라 다른 방식의 디렉터리에 권한을 부여")
    @EnumSource(PermissionType.class)
    @EnabledOnOs(OS.WINDOWS)
    void grantOnDirectory_on_WINDOWS(final PermissionType permissionType) throws IOException {
        AclFileAttributeView aclFileAttributeView = Files.getFileAttributeView(Path.of("./resource/"),
                AclFileAttributeView.class);
        permissionType.grantPermission("../../../resources");
        List<AclEntry> aclEntries = aclFileAttributeView.getAcl();
        assertThat(aclEntries).anyMatch(aclEntry -> {
            try {
                return aclEntry.equals(AclEntryFactory.createOwnerEntry(Path.of("../../../resources"))) || aclEntry.equals(AclEntryFactory.createEveryoneEntry(Path.of("../../../resources")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}