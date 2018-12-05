package com.casasw.iddog;

import java.lang.ref.WeakReference;

interface DogPresenterInput {
    public void presentDogActivityData(DogResponse response);
}


public class DogPresenter implements DogPresenterInput {

    public static String TAG = DogPresenter.class.getSimpleName();

    //weak var output: HomePresenterOutput!
    public WeakReference<DogActivityInput> output;


    @Override
    public void presentDogActivityData(DogResponse response) {
        // Log.e(TAG, "presentDogActivityData() called with: mResponse = [" + mResponse + "]");
        //Do your decoration or filtering here

    }


}
