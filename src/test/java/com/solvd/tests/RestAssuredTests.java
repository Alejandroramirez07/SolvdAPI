package com.solvd.tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredTests {

    private static final String BASE = "https://jsonplaceholder.typicode.com";

    @Test
    public void getUsers() {
        given()
                .when().get(BASE + "/users")
                .then().statusCode(200);
    }

    @Test
    public void getUserById() {
        given()
                .when().get(BASE + "/users/1")
                .then().statusCode(200);
    }

    @Test
    public void createUser() {
        given()
                .body("{\"name\":\"Test\",\"username\":\"test123\"}")
                .header("Content-Type", "application/json")
                .when()
                .post(BASE + "/users")
                .then()
                .statusCode(201);
    }

    @Test
    public void updateUser() {
        given()
                .body("{\"name\":\"Updated\"}")
                .header("Content-Type", "application/json")
                .when()
                .put(BASE + "/users/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteUser() {
        given()
                .when()
                .delete(BASE + "/users/1")
                .then()
                .statusCode(200);
    }
}

