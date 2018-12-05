package com.casasw.iddog;

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
        LoginResponse loginResponse = new LoginResponse();
        LoginWorker loginWorker = new LoginWorker();
        loginWorker.setLoginData(request.getLogin());

        loginResponse.setLoginJson(loginWorker.getLoginData());



        output.presentLoginData(loginResponse);
    }
}
