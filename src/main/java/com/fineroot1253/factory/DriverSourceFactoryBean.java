package com.fineroot1253.factory;

import com.fineroot1253.chrome.ChromeDriverSourceCreator;
import com.fineroot1253.driver.DriverSourceCreator;
import com.fineroot1253.util.ExceptionMessage;

public class DriverSourceFactoryBean {
    private DriverSourceFactoryBean(){
        throw new IllegalStateException(ExceptionMessage.CREATE_UTILITY_CLASS_EXCEPTION);
    }

    public static DriverSourceCreator createCreator(){
        return new ChromeDriverSourceCreator();
    }
}
