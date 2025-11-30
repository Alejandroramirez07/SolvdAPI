package com.solvd.tests;

import com.solvd.api.methods.*;
import com.zebrunner.carina.core.AbstractTest;
import org.testng.annotations.Test;

public class DeclarativeApiTests extends AbstractTest {

    @Test
    public void testGetUsersValid() {
        GetUsersMethod api = new GetUsersMethod();
        api.callAPI();
        api.validateResponse();
    }

    @Test
    public void testGetUserValid() {
        GetUserMethod api = new GetUserMethod(1);
        api.callAPI();
        api.validateResponse();
    }

    @Test
    public void testCreateUserValid() {
        CreateUserMethod api = new CreateUserMethod();
        api.setProperty("name", "Daniel");
        api.setProperty("username", "newuser");
        api.callAPI();
        api.validateResponse();
    }

    @Test
    public void testUpdateUserWithTemplate() {
        UpdateUserMethod api = new UpdateUserMethod(1);
        api.setProperty("name", "Updated Daniel");
        api.callAPI();
        api.validateResponse();
    }

    @Test
    public void testDeleteUserValid() {
        DeleteUserMethod api = new DeleteUserMethod(1);
        api.callAPI();
        api.validateResponse();
    }
}
