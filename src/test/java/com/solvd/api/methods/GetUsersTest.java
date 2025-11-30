package com.solvd.api.methods;

import com.zebrunner.carina.core.AbstractTest;
import org.testng.annotations.Test;

public class GetUsersTest extends AbstractTest {

    @Test
    public void testGetUsers() {
        GetUserMethod api = new GetUserMethod(1);
        api.callAPI();
        api.validateResponse();
    }
}

