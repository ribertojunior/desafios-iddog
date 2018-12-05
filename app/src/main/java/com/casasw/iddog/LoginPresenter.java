package com.casasw.iddog;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
        String email = "";
        Log.d(TAG, response.getLoginJson());

        try {
            JSONObject userEmail = new JSONObject(response.getLoginJson());
            email = userEmail.getString("user");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        LoginViewModel loginModel = new LoginViewModel(email);

        output.get().displayLoginData(loginModel);

    }


}
