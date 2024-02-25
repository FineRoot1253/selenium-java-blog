package com.fineroot1253.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChromeVersion {
    private final List<ChromeVersionItem> versions;

    public ChromeVersion(final String version) {
        validate(version);
        this.versions = Arrays.stream(version.split("\\.")).map(ChromeVersionItem::new).collect(Collectors.toList());
    }

    private void validate(final String version) {
        if (version.split("\\.").length != 4) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isLatestVersionThan(final ChromeVersion version) {
        return IntStream.range(0, 4)
                .allMatch(value -> versions.get(value).isLatestEqThan(version.getItemVersion(value)));
    }

    private ChromeVersionItem getItemVersion(int index) {
        return versions.get(index);
    }
}
