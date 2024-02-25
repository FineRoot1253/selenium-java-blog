package com.fineroot1253.driver;

import com.fineroot1253.util.OsType;
import java.io.IOException;

public interface DriverUpdater {
    void updateToLatestVersion(final OsType osType) throws IOException;
}
