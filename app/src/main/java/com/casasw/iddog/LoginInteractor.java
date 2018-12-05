package com.casasw.iddog;

import android.os.AsyncTask;

interface LoginInteractorInput {
    public void fetchLoginData(LoginRequest request);
}


public class LoginInteractor implements LoginInteractorInput {

    public static String TAG = LoginInteractor.class.getSimpleName();
    public LoginPresenterInput output;
    public LoginWorkerInput aLoginWorkerInput;

    public LoginWorkerInput getLoginWorkerInput() {
        if (aLoginWorkerInput == null) return new LoginWorker();
        return aLoginWorkerInput;
    }

    public void setLoginWorkerInput(LoginWorkerInput aLoginWorkerInput) {
        this.aLoginWorkerInput = aLoginWorkerInput;
    }

    @Override
    public void fetchLoginData(LoginRequest request) {
        aLoginWorkerInput = getLoginWorkerInput();
        SignUpTask task = new SignUpTask();
        task.execute(request.getLogin().getUser());
        /*LoginResponse loginResponse = new LoginResponse();
        LoginWorker loginWorker = new LoginWorker();
        loginWorker.setLoginData(request.getLogin());

        loginResponse.setLoginJson(loginWorker.getLoginData());



        output.presentLoginData(loginResponse);*/
    }

    private class SignUpTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setLoginJson(s);
            output.presentLoginData(loginResponse);
        }

        @Override
        protected String doInBackground(String... strings) {
            LoginWorker loginWorker = new LoginWorker();
            loginWorker.setLoginData(new LoginModel(strings[0]));

            return loginWorker.getLoginData();
        }
    }
}


