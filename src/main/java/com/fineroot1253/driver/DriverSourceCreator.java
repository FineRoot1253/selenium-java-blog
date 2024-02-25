package com.fineroot1253.driver;

import java.io.IOException;

public interface DriverSourceCreator {
    DriverSource create(final String path) throws IOException;
}
