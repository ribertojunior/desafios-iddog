package com.casasw.iddog;

import java.lang.ref.WeakReference;

interface LoginPresenterInput {
    public void presentLoginData(LoginResponse response);
}


public class LoginPresenter implements LoginPresenterInput {

    public static String TAG = LoginPresenter.class.getSimpleName();

    //weak var output: HomePresenterOutput!
    public WeakReference<LoginActivityInput> output;


    @Override
    public void presentLoginData(LoginResponse response) {
        // Log.e(TAG, "presentLoginData() called with: mResponse = [" + mResponse + "]");
        //Do your decoration or filtering here

        output.get().displayLoginData(response.getLoginViewModel());

    }


}
