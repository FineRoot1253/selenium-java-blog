package com.fineroot1253.chrome;

import static com.fineroot1253.util.ExceptionMessage.CREATE_CHROME_DRIVER_MANAGER_EXCEPTION;

import com.fineroot1253.driver.DriverSourceCreator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChromeDriverSourceCreator implements DriverSourceCreator {

    @Override
    public ChromeDriverSource create(final String path) throws IOException {
        return new ChromeDriverSource(path, parseVersion(path));
    }

    private String parseVersion(final String path) throws IOException {
        Process process = new ProcessBuilder(path, "--version").start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String versionString = bufferedReader.readLine();
        if (!versionString.startsWith("ChromeDriver")){
            throw new IllegalStateException(CREATE_CHROME_DRIVER_MANAGER_EXCEPTION.concat(versionString));
        }
        process.destroy();
        String[] split = versionString.split(" ");
        return split[1];
    }
}
