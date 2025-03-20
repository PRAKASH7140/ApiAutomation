# API Automation Framework

## Overview
This project is an automated API testing framework built using **RestAssured (Java)** and **TestNG** for testing RESTful APIs. The framework validates various API functionalities, including authentication, CRUD operations, and error handling, while generating detailed test reports using **Extent Reports**.

## Features
- **Comprehensive API Test Coverage**
  - Automates CRUD operations (Create, Read, Update, Delete)
  - Validates response codes, payloads, headers, and error handling
  - Covers positive and negative test scenarios
  - Implements **request chaining** where needed
- **Framework Implementation**
  - Uses **RestAssured** for API testing
  - Integrated with **TestNG** for test execution
  - Supports **configurable environments** using a `config.properties` file
  - Implements **data-driven testing** with `@DataProvider`
  - Provides **proper assertions** for validation
- **Execution & Reporting**
  - Generates **detailed Extent Reports** with test results (Pass/Fail/Skip)
  - Supports **parallel test execution** with TestNG
  - Captures request/response logs for debugging


## Project Structure
```
api-automation-framework/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/api/testing/RestAssuredTest/ (Test Cases)
│   │   │   ├── com/api/testing/RestAssuredTest/base/ (Base Test Class)
│   │   │   ├── com/api/testing/RestAssuredTest/utils/ (Utilities - ConfigReader, ReportManager)
│   ├── test/
│── test-output/ (Generated Reports)
│── config.properties (Configuration Settings)
│── pom.xml (Maven Dependencies)
│── README.md (This File)
```

## Setup Instructions
### Prerequisites
- **Java 11+** installed
- **Maven** installed
- **TestNG** and **RestAssured** dependencies (already in `pom.xml`)
- API Server running (update `base.url` in `config.properties`)

### Installation Steps
1. **Clone the Repository:**
   ```sh
   git clone URL
   cd api-automation-framework
   ```
2. **Configure API Base URL:**
   - Update `config.properties` with the correct API URL.

3. **Run Tests:**
   ```sh
   mvn clean test
   ```
   OR
   ```sh
   mvn test 
   ```

4. **View Reports:**
   - Reports are generated in `test-output/ExtentReport.html`.
   - Open the file in a browser.

## Test Execution
- Run all tests:
  ```sh
  mvn test
  ```
- Run a specific test:
  ```sh
  mvn test -Dtest=AppTest#testUserLogin
  ```
- Run tests in parallel:
  ```sh
  mvn test -Dgroups=smoke
  ```

## Reporting
- The framework uses **Extent Reports** to generate a structured test execution report.
- The report includes **status (Pass/Fail/Skip)**, logs, request/response details, and execution time.
- Reports are automatically opened after test execution.

## Test Strategy
1. **Test Coverage:**
   - All critical API functionalities are tested.
   - Both positive and negative scenarios are covered.
   - Error handling is validated.
2. **Maintainability & Scalability:**
   - Modular test structure using `BaseTest.java`.
   - Utility methods for reusable API calls.
   - Data-driven approach for flexibility.
3. **Challenges & Solutions:**
   - **Handling dynamic tokens:** Implemented `BaseTest.setToken()` to store session tokens.
   - **Request chaining:** Extracting API responses and using them in subsequent requests.
   - **Flaky test retries:** Configured TestNG retry mechanism for transient failures.

---
Happy Testing! 🚀

