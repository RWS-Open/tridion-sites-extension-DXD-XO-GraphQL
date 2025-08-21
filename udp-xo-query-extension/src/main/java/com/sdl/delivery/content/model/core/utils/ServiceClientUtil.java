package com.sdl.delivery.content.model.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ServiceClientUtil {

    public static String getOsEndpoint() {
        String scheme = AppPropertiesReader.getProperty("os.scheme");
        String host = AppPropertiesReader.getProperty("os.host");
        String port = AppPropertiesReader.getProperty("os.port");
        return scheme + "://" + host + ":" + port;
    }

    public static String getAuthHeader() {
        String username = AppPropertiesReader.getProperty("os.username");
        String password = AppPropertiesReader.getProperty("os.password");
        return "Basic " + Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    public static OkHttpClient getClient() {
        return new OkHttpClient();
    }

    public static ObjectMapper getMapper() {
        return new ObjectMapper();
    }
}
