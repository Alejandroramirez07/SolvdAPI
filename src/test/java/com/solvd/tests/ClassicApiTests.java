package com.solvd.tests;

import com.solvd.api.methods.*;
import com.zebrunner.carina.core.AbstractTest;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClassicApiTests extends AbstractTest {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(ClassicApiTests.class);

    @Test
    public void testGetUsers() {
        GetUsersMethod api = new GetUsersMethod();
        api.callAPI();
        api.validateResponse(JSONCompareMode.LENIENT);
    }

    @Test
    public void testGetUserById() {
        GetUserMethod api = new GetUserMethod(1);
        api.callAPI();
        api.validateResponse(JSONCompareMode.LENIENT);
    }

    @Test
    public void testCreateUser() {
        CreateUserMethod api = new CreateUserMethod();
        api.setProperties("api/users/user.properties");
        api.callAPI();
        api.validateResponse(JSONCompareMode.LENIENT);
    }

    @Test
    public void testUpdateUser() {
        UpdateUserMethod api = new UpdateUserMethod(1);
        api.setProperties("api/users/update-user.properties");

        api.callAPI();
        LOGGER.info("Response: " + api.getResponse());
    }

    @Test
    public void testDeleteUser() {
        DeleteUserMethod api = new DeleteUserMethod(1);
        api.callAPI();

        LOGGER.info("Status: " + api.getResponse());
    }
}

