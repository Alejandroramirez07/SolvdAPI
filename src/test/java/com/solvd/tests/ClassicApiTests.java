package com.solvd.tests;

import com.solvd.api.methods.*;
import com.zebrunner.carina.core.AbstractTest;
import org.testng.annotations.Test;

public class ClassicApiTests extends AbstractTest {

    @Test
    public void testGetUsers() {
        GetUsersMethod api = new GetUsersMethod();
        api.callAPI();
        api.validateResponse();
    }

    @Test
    public void testGetUserById() {
        GetUserMethod api = new GetUserMethod(1);
        api.callAPI();
        api.validateResponse();
    }

    @Test
    public void testCreateUser() {
        CreateUserMethod api = new CreateUserMethod();
        api.setProperties("name=John;username=johnny;email=john@test.com");
        api.callAPI();
        api.validateResponse();
    }

    @Test
    public void testUpdateUser() {
        UpdateUserMethod api = new UpdateUserMethod(1);
        api.setProperties("name=Updated;username=newuser");
        api.callAPI();
        api.validateResponse();
    }

    @Test
    public void testDeleteUser() {
        DeleteUserMethod api = new DeleteUserMethod(1);
        api.callAPI();
        api.validateResponse();
    }
}

