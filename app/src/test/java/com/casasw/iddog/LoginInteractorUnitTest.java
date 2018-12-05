package com.casasw.iddog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LoginInteractorUnitTest {
    @Before
    public void setUp(){

    }
    @After
    public void tearDown(){

    }

    @Test
    public void testAsync(){
        LoginRequest loginRequest = new LoginRequest(new LoginModel("ribertojunior@gmail.com"));
        LoginPresenterSpy spy = new LoginPresenterSpy();
        LoginInteractor interactor = new LoginInteractor();
        interactor.output = spy;
        interactor.fetchLoginData(loginRequest);

        Assert.assertTrue(spy.presentLoginDataIsCalled);
    }

    private class LoginPresenterSpy implements LoginPresenterInput {
        boolean presentLoginDataIsCalled = false;

        @Override
        public void presentLoginData(LoginResponse response) {
            presentLoginDataIsCalled = true;
        }
    }


}
