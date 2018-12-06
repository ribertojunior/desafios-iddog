package com.casasw.iddog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DogWorkerTest {
    public static final String TAG = DogWorkerTest.class.getSimpleName();
    @Before
    public void setUp(){}
    @After
    public void tearDown(){}

    @Test
    public void testApi(){
        String token = "";

        DogRequest dogRequest = new DogRequest(new DogModel(token, "husky"));
        DogWorker dogWorker = new DogWorker();
        dogWorker.setmDogModel(dogRequest.getDogModel());
        String json = dogWorker.getDogData();
        System.out.println(TAG + " - " + json);
        Assert.assertNotEquals(json, "{ \"error\": \"Response unsuccessful\" }");
        Assert.assertNotEquals(json, "{ \"error\": \"Body's null, mind's full\" }");


    }


}
