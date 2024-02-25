package com.fineroot1253.util;

import static com.fineroot1253.util.ExceptionMessage.CREATE_CHROME_VERSION_ITEM_EXCEPTION;

public class ChromeVersionItem {
    private final int version;

    public ChromeVersionItem(final String version) {
        validate(version);
        this.version = Integer.parseInt(version);
    }

    private void validate(final String version) {
        if (!version.matches("\\d+")) {
            throw new IllegalArgumentException(CREATE_CHROME_VERSION_ITEM_EXCEPTION);
        }
    }

    public boolean isLatestEqThan(final ChromeVersionItem version) {
        return this.version >= version.version;
    }
}
