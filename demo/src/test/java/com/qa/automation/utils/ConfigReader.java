package com.qa.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads the framework settings from {@code config/config.properties}.
 *
 * <p>Think of this as a single place to keep environment-specific values
 * (URLs, browser choice, etc.) instead of hard-coding them in tests.
 *
 * <p>The file is loaded once, when the class is first used, and the values
 * are kept in memory for the whole test run.
 */
public final class ConfigReader {

    // Holds every key/value loaded from the properties file.
    private static final Properties PROPERTIES = new Properties();

    // Static block = runs once when the class is loaded.
    static {
        // getResourceAsStream finds the file on the classpath (src/test/resources).
        try (InputStream is = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (is == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // Utility class: prevent anyone creating an instance with "new ConfigReader()".
    private ConfigReader() {
    }

    /**
     * Returns the value for a given key.
     *
     * @param key the setting name, e.g. "base.url"
     * @return the value, never null (throws if the key is missing)
     */
    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing config key: " + key);
        }
        return value;
    }

    /**
     * Returns the value for a key, or a fallback if the key is absent.
     */
    public static String get(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }
}
