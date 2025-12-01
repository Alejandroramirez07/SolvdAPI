package com.solvd.tests;

import com.solvd.api.methods.*;
import com.zebrunner.carina.core.AbstractTest;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class DeclarativeApiTests extends AbstractTest {

    @Test
    public void testGetUsersValid() {
        GetUsersMethod api = new GetUsersMethod();
        api.callAPI();
        api.validateResponse(JSONCompareMode.LENIENT);
    }

    @Test
    public void testGetUserValid() {
        GetUserMethod api = new GetUserMethod(1);
        api.callAPI();
        api.validateResponse(JSONCompareMode.LENIENT);
    }

    @Test
    public void testCreateUserValid() {
        CreateUserMethod api = new CreateUserMethod();
        api.setProperties("api/users/user.properties");

        api.callAPI();

        api.validateResponseAgainstSchema("api/users/user-schema.json");
        api.validateResponse(JSONCompareMode.LENIENT);
    }

    @Test
    public void testUpdateUserWithTemplate() {
        UpdateUserMethod api = new UpdateUserMethod(1);
        api.setProperties("api/users/update-user.properties");

        api.callAPI();

        System.out.println("API call completed successfully!");
        System.out.println("Response: " + api.getResponse());
    }

    @Test
    public void testDeleteUserValid() {
        DeleteUserMethod api = new DeleteUserMethod(1);
        api.callAPI();

        System.out.println(" DELETE request completed successfully!");
        System.out.println("Status: " + api.getResponse());
    }
}
