package com.fineroot1253.util;

import com.fineroot1253.factory.AclEntryFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public enum PermissionType {
    POSIX("POSIX", path -> {
        try {
            Files.setPosixFilePermissions(Path.of(path), PosixFilePermissions.fromString("rwxr-xr-x"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }),
    WIN("WIN", path -> {
        try {
            Path aPath = Path.of(path);
            setAclAttributeView(aPath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }),

    OTHER("OTHER", path -> {
        throw new IllegalStateException("Unsupported OS");
    });

    private final String osApiName;
    private final Consumer<String> grant;

    private static final Map<String, List<String>> cacheMap = new HashMap<>(
            Map.of("POSIX", List.of("nix", "nux", "aix", "linux", "free", "mac", "hp-ux", "sun os", "sunos", "solaris"),
                    "WIN", List.of("win", "windows"), "OTHER", new ArrayList<>()));

    PermissionType(final String osApiName, final Consumer<String> grant){
        this.osApiName = osApiName;
        this.grant = grant;
    }

    public static PermissionType from(String osName) {
        return Arrays.stream(PermissionType.values())
                .filter(permissionType -> cacheMap.get(permissionType.osApiName).contains(osName))
                .findFirst()
                .orElse(PermissionType.OTHER);
    }

    private static void setAclAttributeView(Path aPath) throws IOException {
        AclFileAttributeView view = Files.getFileAttributeView(aPath, AclFileAttributeView.class);
        if (view != null) {
            List<AclEntry> aclList = view.getAcl();
            aclList.add(AclEntryFactory.createOwnerEntry(aPath));
            aclList.add(AclEntryFactory.createEveryoneEntry(aPath));
            view.setAcl(aclList);
        }
    }

    public void grantPermission(final String path) throws IllegalStateException{
        this.grant.accept(path);
    }
}
