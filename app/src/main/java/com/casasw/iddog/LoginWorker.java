package com.casasw.iddog;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

interface LoginWorkerInput {
    String getLoginData();
    public void setLoginData(LoginModel login);

}

public class LoginWorker implements LoginWorkerInput {
    private String mJSON;
    private LoginModel mLoginModel;

    static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

    @Override
    public String getLoginData() {
        mJSON = "{ \"error\": \"Login Model is null\" }";

        if (mLoginModel!=null) {
            mJSON = "{ \"email\": \""+mLoginModel.getUser()+"\" }";
        }
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api-iddog.idwall.co/signup")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN,mJSON))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                mJSON = "{ \"error\": \"Response unsuccessful\" }";
            } else {
                if (response.body() != null) {
                    mJSON = response.body().string();
                } else
                    mJSON = "{ \"error\": \"Body's null, mind's full\" }";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }





        return mJSON;
    }

    @Override
    public void setLoginData(LoginModel login) {
        mLoginModel = login;
    }


}


