package com.fineroot1253.chrome;

import com.fineroot1253.driver.DriverSource;
import com.fineroot1253.util.Api;
import com.fineroot1253.util.ChromeVersion;
import com.fineroot1253.util.URLs;
import java.io.IOException;
import java.nio.file.Path;

public class ChromeDriverSource implements DriverSource {
    private final String path;
    private final ChromeVersion version;

    public ChromeDriverSource(final String path, final String version) {
        this.path = path;
        this.version = new ChromeVersion(version);
    }

    @Override
    public boolean needUpdate() throws IOException {
        return !version.isLatestVersionThan(new ChromeVersion(Api.get(URLs.CHROME_DRIVER_LATEST_VERSION_URL)));
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getZipPath() {
        return Path.of(path).getParent().toString().concat(".zip");
    }

    @Override
    public String getAncientPath() {
        return Path.of(path).getParent().getParent().toString();
    }
}
