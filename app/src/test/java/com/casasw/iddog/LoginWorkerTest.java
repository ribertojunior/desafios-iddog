package com.casasw.iddog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LoginWorkerTest {
    public static final String TAG = LoginWorkerTest.class.getSimpleName();
    @Before
    public void setUp(){}
    @After
    public void tearDown(){}

    @Test
    public void testApi(){
        LoginWorker loginWorker = new LoginWorker();
        LoginRequest loginRequest = new LoginRequest(new LoginModel("ribertojunior@gmail.com"));
        loginWorker.setLoginData(loginRequest.getLogin());
        String json = loginWorker.getLoginData();
        System.out.println(TAG + " - " + json);
        Assert.assertNotEquals(json, "{ \"error\": \"Response unsuccessful\" }");
        Assert.assertNotEquals(json, "{ \"error\": \"Body's null, mind's full\" }");


    }
}
