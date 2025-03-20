package com.api.testing.RestAssuredTest.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import com.api.testing.RestAssuredTest.utils.ConfigReader;

public class BaseTest {
    protected static String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigReader.getProperty("base.url");
    }

    protected static void setToken(String accessToken) {
        token = accessToken;
    }
}
