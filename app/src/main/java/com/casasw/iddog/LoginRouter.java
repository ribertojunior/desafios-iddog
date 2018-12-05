package com.casasw.iddog;

import android.content.Intent;

import java.lang.ref.WeakReference;


interface LoginRouterInput {

    public void passDataToNextScene(LoginViewModel viewMode, Intent intent);
}

public class LoginRouter implements LoginRouterInput {

    public static String TAG = LoginRouter.class.getSimpleName();
    public WeakReference<LoginActivity> activity;




    @Override
    public void passDataToNextScene(LoginViewModel viewModel, Intent intent) {
        intent.putExtra("EXTRA_USER", viewModel.getUser());
        intent.putExtra("EXTRA_TOKEN", viewModel.getToken());
        activity.get().startActivity(intent);
    }




}
