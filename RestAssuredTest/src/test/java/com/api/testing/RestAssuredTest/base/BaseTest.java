package com.api.testing.RestAssuredTest.base;

import com.api.testing.RestAssuredTest.utils.ConfigReader;
import com.api.testing.RestAssuredTest.utils.ReportManager;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    protected static String token;

    @BeforeClass
    public void setup() {
        try {
            RestAssured.baseURI = ConfigReader.getProperty("base.url");
            ReportManager.startTest(getClass().getSimpleName()); // Start report per class
            System.out.println("✅ Base URI set to: " + RestAssured.baseURI);
        } catch (Exception e) {
            System.err.println("❌ Error setting up Base URI: " + e.getMessage());
        }
    }

    protected static void setToken(String accessToken) {
        if (token == null || token.isEmpty()) { 
            token = accessToken;
            System.out.println("🔑 Token initialized successfully.");
        }
    }

    @AfterMethod
    public void logTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ReportManager.logError("❌ FAILED: " + result.getName() + " - " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ReportManager.logPass("✅ PASSED: " + result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ReportManager.logSkip("⚠️ SKIPPED: " + result.getName());
        }
    }

    @AfterClass
    public void tearDown() {
        ReportManager.endTest();
    }
}
