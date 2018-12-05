package com.casasw.iddog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


interface DogActivityInput {
    public void displayDogActivityData(DogViewModel viewModel);
}


public class DogActivity extends AppCompatActivity
        implements DogActivityInput {

    public static String TAG = DogActivity.class.getSimpleName();
    DogInteractorInput output;
    DogRouter router;
    LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        //do the setup

        DogConfigurator.INSTANCE.configure(this);

        mLoginViewModel = new LoginViewModel(
                getIntent().getStringExtra("EXTRA_USER"), getIntent().getStringExtra("EXTRA_TOKEN"));
        DogRequest aDogRequest = new DogRequest(new DogModel(mLoginViewModel.getToken()));
        //populate the request


//        output.fetchDogActivityData(aDogRequest);
        // Do other work
    }


    @Override
    public void displayDogActivityData(DogViewModel viewModel) {
        Log.e(TAG, "displayDogActivityData() called with: viewModel = [" + viewModel + "]");
        // Deal with the data
    }
}
