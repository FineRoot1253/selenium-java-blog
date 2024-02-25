package com.fineroot1253.chrome;

import com.fineroot1253.driver.DriverSource;
import com.fineroot1253.driver.DriverSourceCreator;
import com.fineroot1253.driver.DriverUpdater;
import com.fineroot1253.factory.DriverSourceFactoryBean;
import com.fineroot1253.util.Api;
import com.fineroot1253.util.FileUtils;
import com.fineroot1253.util.OsType;
import com.fineroot1253.util.URLs;
import java.io.IOException;

public class ChromeDriverManager implements DriverUpdater {
    private DriverSource driverSource;

    public ChromeDriverManager(final String path) throws IOException {
        DriverSourceCreator creator = DriverSourceFactoryBean.createCreator();
        this.driverSource = creator.create(path);
    }

    @Override
    public void updateToLatestVersion(final OsType osType) throws IOException {
        if(driverSource.needUpdate()){
            FileUtils.download(osType.replaceURL(Api.get(URLs.CHROME_DRIVER_LATEST_VERSION_URL)), driverSource.getZipPath());
            FileUtils.unzip(driverSource.getZipPath(), driverSource.getAncientPath());
            DriverSourceCreator creator = DriverSourceFactoryBean.createCreator();
            this.driverSource = creator.create(driverSource.getPath());
        }
    }
}
