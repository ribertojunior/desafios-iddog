package com.casasw.iddog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityUnitTest {



    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testLoginActivityNotNull(){
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);

        Assert.assertNotNull(activity);
    }

    @Test
    public void testClickOnSingIn(){
        LoginActivityOutputSpy loginActivityOutputSpy = new LoginActivityOutputSpy();
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        activity.output = loginActivityOutputSpy;
        activity.findViewById(R.id.email_sign_in_button).performClick();
        activity.fetchLoginData();
        Assert.assertTrue(loginActivityOutputSpy.fetchLoginDataIsCalled);
    }

    @Test
    public void testFetchLoginData(){

    }

    @Test
    public void testVerifyEmail(){
        String[] email = {"a@a.com", "", "a12123@1.com",
                "a.com", "2@", "asdasd@asadas.com.br", "vxcvxc@cvadsfq.co"};
        boolean[] expected = {true, false, true, false, false, true, true};
        int i = 0;
        for (String subject :
                email) {
            assertEquals(expected[i], LoginActivity.verifyEmail(subject));
            i++;
        }
        int left = 32, right=122, length=10;
        Random random = new Random();
        StringBuilder stringBuilder;
        int limitedInt;
        String subject = "";

        for (i = 0; i < 1000; i++){
            stringBuilder = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                limitedInt = left + (int)
                        (random.nextFloat() * (right - left + 1));
                stringBuilder.append((char) limitedInt);
            }
            subject = stringBuilder.toString();
            //System.out.println(subject);
            if(!(subject.contains("@") && subject.contains("."))) {
                Assert.assertFalse(LoginActivity.verifyEmail(subject));
            }
            else {
                System.out.println("Prop valid: "+subject);
            }

        }
        left = 97; right = 122; length = 64; int len2 = 250;
        int s = 0;
        for (i = 0; i < 1000; i++){
            s = random.nextInt(length) + 1;
            stringBuilder = new StringBuilder(320);
            for (int j = 0; j < s; j++) {
                limitedInt = left + (int)
                        (random.nextFloat() * (right - left + 1));
                stringBuilder.append((char) limitedInt);
            }
            stringBuilder.append('@');
            s = random.nextInt(len2) + 1;
            for (int j = 0; j < s; j++) {
                limitedInt = left + (int)
                        (random.nextFloat() * (right - left + 1));
                stringBuilder.append((char) limitedInt);
            }
            stringBuilder.append('.');
            s = random.nextInt(2) + 2;
            for (int j = 0; j < s; j++) {
                limitedInt = left + (int)
                        (random.nextFloat() * (right - left + 1));
                stringBuilder.append((char) limitedInt);
            }
            subject = stringBuilder.toString();
            System.out.println(subject);
            Assert.assertTrue(LoginActivity.verifyEmail(subject));
            stringBuilder.append('.');
            for (int j = 0; j < 2; j++) {
                limitedInt = left + (int)
                        (random.nextFloat() * (right - left + 1));
                stringBuilder.append((char) limitedInt);
            }
            subject = stringBuilder.toString();
            System.out.println(subject);
            Assert.assertTrue(LoginActivity.verifyEmail(subject));


        }

    }

    @Test
    public void testDisplayLoginData(){

    }

    private class LoginActivityOutputSpy implements LoginInteractorInput {
        boolean fetchLoginDataIsCalled = false;
        LoginRequest loginRequestCopy;
        @Override
        public void fetchLoginData(LoginRequest request) {
            fetchLoginDataIsCalled = true;
            loginRequestCopy = request;
        }
    }
}
