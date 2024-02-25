package com.fineroot1253.util;

import static com.fineroot1253.util.ExceptionMessage.CREATE_OS_TYPE_EXCEPTION;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum OsType {
    LINUX_AMD64("LINUX","x86_64","linux64", PermissionType.POSIX),
    MAC_AMD64("MAC","x86_64","mac-x64", PermissionType.POSIX),
    MAC_ARM64("MAC","arm64","mac-arm64", PermissionType.POSIX),
    WIN_AMD32("WIN","x86","win32", PermissionType.WIN),
    WIN_AMD64("WIN","x86_64","win64", PermissionType.WIN);

    private final String name;
    private final String urlPlatformName;
    private final String archName;
    private final PermissionType permissionType;

    private static final Map<String, List<String>> osCacheMap = new HashMap<>(
            Map.of("LINUX", List.of("linux"),
                    "WIN", List.of("win", "windows"),
                    "MAC", List.of("macos", "mac")));

    private static final String BASE_URL = "https://storage.googleapis.com/chrome-for-testing-public/VERSION/OS/chromedriver-OS.zip";

    OsType(final String name, final String archName, final String urlPlatformName, final PermissionType permissionType) {
        this.name = name;
        this.archName = archName;
        this.urlPlatformName = urlPlatformName;
        this.permissionType = permissionType;
    }

    public static OsType from(final String osName, final String archName) {
        return Arrays.stream(OsType.values())
                .filter(osType -> osCacheMap.get(osType.name).contains(osName) && osType.archName.equals(archName))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(CREATE_OS_TYPE_EXCEPTION));
    }

    public String replaceURL(final String version){
        return BASE_URL.replace("OS", this.urlPlatformName).replace("VERSION", version);
    }

    public void grantPermission(final String path) throws IllegalStateException {
        this.permissionType.grantPermission(path);
    }
}
