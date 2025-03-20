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
            // Get environment (local or CI/CD)
            String env = System.getProperty("env", ConfigReader.getProperty("env", "local")); 
            String baseUrl = ConfigReader.getProperty("base.url." + env, "http://127.0.0.1:8000"); 

            RestAssured.baseURI = baseUrl;
            ReportManager.startTest(getClass().getSimpleName());

            System.out.println("✅ API Environment: " + env);
            System.out.println("✅ Base URI set to: " + RestAssured.baseURI);
        } catch (Exception e) {
            System.err.println("❌ Error setting up Base URI: " + e.getMessage());
        }
    }

    protected static void setToken(String accessToken) {
        if (token == null || token.isEmpty()) { 
            token = accessToken;
            System.out.println("🔑 Token initialized successfully.");
        } else {
            System.out.println("🔄 Token already set, reusing existing session.");
        }
    }

    @AfterMethod
    public void logTestResult(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                ReportManager.logError("❌ FAILED: " + result.getName() + " - " + result.getThrowable());
                break;
            case ITestResult.SUCCESS:
                ReportManager.logPass("✅ PASSED: " + result.getName());
                break;
            case ITestResult.SKIP:
                ReportManager.logSkip("⚠️ SKIPPED: " + result.getName());
                break;
        }
    }

    @AfterClass
    public void tearDown() {
        ReportManager.endTest();
        System.out.println("🛑 Test Execution Completed.");
    }
}
