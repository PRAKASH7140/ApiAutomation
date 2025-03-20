package com.api.testing.RestAssuredTest.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static final String REPORT_PATH = "test-output/ExtentReport.html";

    static {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(REPORT_PATH);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public static void startTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void logInfo(String message) {
        test.info(message);
    }

    public static void logError(String message) {
        test.fail(message);
    }

    public static void endTest() {
        extent.flush();
        openReport();
    }

    private static void openReport() {
        try {
            File reportFile = new File(REPORT_PATH);
            if (reportFile.exists()) {
                Desktop.getDesktop().browse(reportFile.toURI());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

