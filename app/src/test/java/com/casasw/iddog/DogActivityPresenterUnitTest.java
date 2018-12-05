package com.casasw.iddog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by mkaratadipalayam on 11/10/16.
 */
@RunWith(RobolectricTestRunner.class)

public class DogActivityPresenterUnitTest {
    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testDogActivityNotNull(){
        DogActivity dogActivity = Robolectric.setupActivity(DogActivity.class);
        Assert.assertNotNull(dogActivity);
    }



    private class DogActivityOutputSpy implements DogInteractorInput {
        boolean fetchDogDataIsCalled = false;
        DogRequest dogRequestCopy;

        @Override
        public void fetchDogActivityData(DogRequest request) {
            fetchDogDataIsCalled = true;
            dogRequestCopy = request;
        }
    }
}
