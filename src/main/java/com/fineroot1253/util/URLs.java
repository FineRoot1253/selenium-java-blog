package com.fineroot1253.util;

import static com.fineroot1253.util.ExceptionMessage.CREATE_UTILITY_CLASS_EXCEPTION;

public class URLs {
    private URLs(){
        throw new IllegalStateException(CREATE_UTILITY_CLASS_EXCEPTION);
    }
    public static final String CHROME_DRIVER_LATEST_VERSION_URL = "https://googlechromelabs.github.io/chrome-for-testing/LATEST_RELEASE_STABLE";
}
