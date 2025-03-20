package com.api.testing.RestAssuredTest.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties = new Properties();

	static {
		try (FileInputStream file = new FileInputStream("src/test/resources/config.properties")) {
			properties.load(file);
		} catch (IOException e) {
			System.err.println("Error: Could not load config.properties. " + e.getMessage());
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key, "");
	}
}
