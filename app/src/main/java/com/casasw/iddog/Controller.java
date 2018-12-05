package com.casasw.iddog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.casasw.iddog.IDDogAPI.BASE_URL;

public class Controller implements Callback<List<DogViewModel>> {


    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IDDogAPI idDogAPI = retrofit.create(IDDogAPI.class);
        /*Call<List<DogViewModel>> call = idDogAPI.loadDog("husky");
        call.enqueue(this);*/
    }
    @Override
    public void onResponse(Call<List<DogViewModel>> call, Response<List<DogViewModel>> response) {
        if (response.isSuccessful()) {
            List<DogViewModel> dogViewModels = response.body();
            for (DogViewModel dog:
                 dogViewModels) {
                System.out.println(dog.getUrl());
            }
        }
    }

    @Override
    public void onFailure(Call<List<DogViewModel>> call, Throwable t) {
        t.printStackTrace();
    }
}
