package com.casasw.iddog;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

interface DogWorkerInput {
    String getDogData();
}

public class DogWorker implements DogWorkerInput {
    private String mJSON;
    private DogModel mDogModel;



    @Override
    public String getDogData() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder builder = HttpUrl.parse("https://api-iddog.idwall.co/feed").newBuilder();
        builder.addQueryParameter("category",mDogModel.getBreed());
        String url = builder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", mDogModel.getToken())
                .header("Content_Type","application/json")
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

    public void setmDogModel(DogModel mDogModel) {
        this.mDogModel = mDogModel;
    }
}
