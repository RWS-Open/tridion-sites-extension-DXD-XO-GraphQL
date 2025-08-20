package com.sdl.delivery.content.model.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppPropertiesReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = AppPropertiesReader.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "application.properties file not found in classpath"
                );
            }
            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error loading application.properties", e);
        }
    }

    public static String getRawProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getProperty(String key) {
        String raw = getRawProperty(key);
        if (raw == null) {
            return null;
        }

        int colonIndex = raw.indexOf(':');
        int endBrace = raw.indexOf('}');
        if (raw.startsWith("${") && colonIndex != -1 && endBrace != -1) {
            return raw.substring(colonIndex + 1, endBrace);
        }

        return raw;
    }

}
