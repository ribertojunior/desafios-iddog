package com.casasw.iddog;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDDogAPI {
    static final String BASE_URL = "https://api-iddog.idwall.co/";


    @GET("feed/")
    Call<ListWrapper<DogViewModel>> loadDog(@Query("q") String category);



}
