package com.fineroot1253.util;

public class ExceptionMessage {

    private ExceptionMessage(){
        throw new IllegalStateException(ExceptionMessage.CREATE_UTILITY_CLASS_EXCEPTION);
    }

    public static final String DIR_CREATE_EXCEPTION = "Failed to create directory ";
    public static final String ENTRY_OUT_OF_TARGET_EXCEPTION = "Entry is outside of target dir: ";
    public static final String CREATE_UTILITY_CLASS_EXCEPTION = "Utility classes can't be created";
}
