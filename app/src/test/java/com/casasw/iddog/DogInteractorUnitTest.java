package com.casasw.iddog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DogInteractorUnitTest {

    @Before
    public void setUp(){

    }
    @After
    public void tearDown(){

    }

    @Test
    public void testAsync(){
        String token = "";
        DogRequest dogRequest = new DogRequest(new DogModel(token, "husky"));
        DogPresenterSpy spy = new DogPresenterSpy();
        DogInteractor dogInteractor = new DogInteractor();
        dogInteractor.output = spy;
        dogInteractor.fetchDogActivityData(dogRequest);

        Assert.assertTrue(spy.presentDogDataIsCalled);
    }

    private class DogPresenterSpy implements DogPresenterInput {
        boolean presentDogDataIsCalled = false;

        @Override
        public void presentDogActivityData(DogResponse response) {
            presentDogDataIsCalled = true;
        }
    }

}
