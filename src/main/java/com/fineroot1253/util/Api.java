package com.fineroot1253.util;

import static com.fineroot1253.util.ExceptionMessage.CREATE_UTILITY_CLASS_EXCEPTION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    private Api(){
        throw new IllegalStateException(CREATE_UTILITY_CLASS_EXCEPTION);
    }

    public static String get(final String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        if (urlConnection.getResponseCode()!= 200){
            throw new IOException();
        }
        return parseResponse(urlConnection.getInputStream());
    }

    private static String parseResponse(final InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        for (String inputLine;( inputLine= reader.readLine())!= null; ) {
            stringBuilder.append(inputLine);
        }
        reader.close();
        return stringBuilder.toString();
    }
}
