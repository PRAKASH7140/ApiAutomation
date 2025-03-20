package com.api.testing.RestAssuredTest.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
    private static final ExtentReports extent = new ExtentReports();
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static String reportPath;

    static {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        reportPath = "test-output/ExtentReport_" + timestamp + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    public static void startTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testThread.set(test);
    }

    public static ExtentTest getTest() {
        return testThread.get();
    }

    public static void logInfo(String message) {
        getTest().info(message);
    }

    public static void logPass(String message) {
        getTest().pass(message);
    }

    public static void logError(String message) {
        getTest().fail(message);
    }

    public static void logSkip(String message) {
        getTest().skip(message);
    }

    public static void endTest() {
        extent.flush();
        openReport();
    }

    private static void openReport() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) { // Only in Local Mode
            try {
                File reportFile = new File(reportPath);
                if (reportFile.exists()) {
                    Desktop.getDesktop().browse(reportFile.toURI());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
