package com.fineroot1253.driver;

import java.io.IOException;

public interface DriverSource {
    boolean needUpdate() throws IOException;

    String getPath();

    String getZipPath();

    String getAncientPath();
}
