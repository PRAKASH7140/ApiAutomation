package com.api.testing.RestAssuredTest.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try (FileInputStream file = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(file);
            System.out.println("✅ Config properties loaded successfully.");
        } catch (IOException e) {
            System.err.println("❌ Error: Could not load config.properties. " + e.getMessage());
        }
    }

    // Get property with default value support
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Overloaded method for backward compatibility (returns empty string if missing)
    public static String getProperty(String key) {
        return getProperty(key, "");
    }
}
