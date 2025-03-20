package com.api.testing.RestAssuredTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import com.api.testing.RestAssuredTest.base.BaseTest;
import com.api.testing.RestAssuredTest.utils.ConfigReader;
import com.api.testing.RestAssuredTest.utils.ReportManager;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.*;

public class AppTest extends BaseTest {
    private String baseUrl;
    private int bookId; // Store book ID for request chaining

    @BeforeClass
    public void setup() {
        String env = System.getProperty("env", ConfigReader.getProperty("env", "local"));  
        baseUrl = ConfigReader.getProperty("base.url." + env, "http://127.0.0.1:8000");

        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalStateException("❌ ERROR: base.url is not set. Check config.properties.");
        }

        RestAssured.baseURI = baseUrl;
        ReportManager.startTest("API Automation Test");

        System.out.println("✅ API running on: " + baseUrl);
    }

    @AfterClass
    public void tearDown() {
        ReportManager.endTest();
        System.out.println("🛑 API Tests Execution Completed.");
    }

    @Test(priority = 1)
    public void testUserSignup() {
        JSONObject request = new JSONObject();
        request.put("email", "dummy35@example.com");
        request.put("password", "dummy123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
            .when()
                .post("/signup")
            .then()
                .statusCode(200)
                .extract().response();

        ReportManager.logInfo("✅ User Signup Response: " + response.asString());
    }

    @Test(priority = 2)
    public void testUserLogin() {
        JSONObject request = new JSONObject();
        request.put("email", "dummy35@example.com");
        request.put("password", "dummy123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
            .when()
                .post("/login")
            .then()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("access_token");
        setToken(token);

        ReportManager.logInfo("✅ User Login Response: " + response.asString());
    }

    @Test(priority = 3, dependsOnMethods = "testUserLogin")
    public void testCreateBook() {
        JSONObject request = new JSONObject();
        request.put("name", "The Catcher in the Rye");
        request.put("author", "J.D. Salinger");
        request.put("published_year", 1951);
        request.put("book_summary", "A story about teenage alienation and angst.");

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
            .when()
                .post("/books/")
            .then()
                .statusCode(200)
                .extract().response();

        bookId = response.jsonPath().getInt("id");
        ReportManager.logInfo("✅ Book Created: " + response.asString());
    }

    @Test(priority = 4, dependsOnMethods = "testCreateBook")
    public void testGetBookById() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
            .when()
                .get("/books/" + bookId)
            .then()
                .statusCode(200)
                .extract().response();

        ReportManager.logInfo("✅ Get Book Response: " + response.asString());
    }

    @Test(priority = 5, dependsOnMethods = "testGetBookById")
    public void testDeleteBook() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
            .when()
                .delete("/books/" + bookId)
            .then()
                .statusCode(200)
                .extract().response();

        ReportManager.logInfo("✅ Book Deleted Response: " + response.asString());
    }

    @Test(priority = 6)
    public void testInvalidLogin() {
        JSONObject request = new JSONObject();
        request.put("email", "invalid@example.com");
        request.put("password", "wrongpassword");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
            .when()
                .post("/login")
            .then()
                .statusCode(400)
                .extract().response();

        ReportManager.logInfo("❌ Invalid Login Response: " + response.asString());
    }

    @Test(priority = 7)
    public void testUnauthorizedAccess() {
        Response response = given()
                .header("Authorization", "Bearer invalidtoken")
            .when()
                .get("/books/1")
            .then()
                .statusCode(403) 
                .extract().response();

        ReportManager.logInfo("❌ Unauthorized Access Response: " + response.asString());
    }

    @DataProvider(name = "bookData")
    public Object[][] bookDataProvider() {
        return new Object[][]{
            {"Book 4", "Author 1", 2001, "Summary 1"},
            {"Book 2", "Author 2", 2002, "Summary 2"},
            {"Book 3", "Author 3", 2003, "Summary 3"}
        };
    }

    @Test(priority = 8, dataProvider = "bookData")
    public void testCreateMultipleBooks(String name, String author, int year, String summary) {
        JSONObject request = new JSONObject();
        request.put("name", name);
        request.put("author", author);
        request.put("published_year", year);
        request.put("book_summary", summary);

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
            .when()
                .post("/books/")
            .then()
                .statusCode(200)
                .extract().response();

        ReportManager.logInfo("✅ Created Book Response: " + response.asString());
    }

    @Test(priority = 9)
    public void testSignupWithExistingEmail() {
        JSONObject request = new JSONObject();
        request.put("email", "dummy32@example.com");
        request.put("password", "dummy123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
            .when()
                .post("/signup")
            .then()
                .statusCode(400)  
                .extract().response();

        ReportManager.logInfo("❌ Duplicate Signup Response: " + response.asString());
    }
    
    @Test(priority = 10)
    public void testLoginWithIncorrectPassword() {
        JSONObject request = new JSONObject();
        request.put("email", "dummy32@example.com");
        request.put("password", "wrongpassword");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
            .when()
                .post("/login")
            .then()
                .statusCode(400)  
                .extract().response();

        ReportManager.logInfo("❌ Login with Wrong Password Response: " + response.asString());
    }
    
    @Test(priority = 11)
    public void testGetBookWithInvalidId() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
            .when()
                .get("/books/99999999") 
            .then()
                .statusCode(404) 
                .extract().response();

        ReportManager.logInfo("❌ Get Book with Invalid ID Response: " + response.asString());
    }
}
