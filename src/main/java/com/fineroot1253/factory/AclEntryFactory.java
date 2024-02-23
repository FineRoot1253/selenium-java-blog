package com.fineroot1253.factory;

import static com.fineroot1253.util.ExceptionMessage.CREATE_UTILITY_CLASS_EXCEPTION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;

public class AclEntryFactory {

    private AclEntryFactory(){
        throw new IllegalStateException(CREATE_UTILITY_CLASS_EXCEPTION);
    }

    public static AclEntry createOwnerEntry(final Path aPath) throws IOException {
        return AclEntry.newBuilder()
                .setType(AclEntryType.ALLOW)
                .setPrincipal(Files.getOwner(aPath))
                .setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.WRITE_ACL,
                        AclEntryPermission.EXECUTE)
                .build();
    }
    public static AclEntry createEveryoneEntry(final Path aPath) throws IOException {
        return AclEntry.newBuilder()
                .setType(AclEntryType.ALLOW)
                .setPrincipal(aPath.getFileSystem().getUserPrincipalLookupService()
                        .lookupPrincipalByName("Everyone"))
                .setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.EXECUTE)
                .build();
    }
}
