package com.casasw.iddog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.casasw.iddog.IDDogAPI.BASE_URL;

@RunWith(RobolectricTestRunner.class)
public class DogWorkerTest {
    public static final String TAG = DogWorkerTest.class.getSimpleName();
    @Before
    public void setUp(){}
    @After
    public void tearDown(){}

    @Test
    public void testApi(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpZGRvZy1zZXJ2aWNlIiwic3ViIjoiNWMwNjdlYTY1YTk0MDE2ZWU3YTVjZjhiIiwiaWF0IjoxNTQzOTI5NTEwLCJleHAiOjE1NDUyMjU1MTB9.U0c-9mH4SsIbvtkouWGu2wSe6r5w6nVrKeimBvd5wvE";

        DogRequest dogRequest = new DogRequest(new DogModel(token));
        DogWorker dogWorker = new DogWorker();
        dogWorker.setmDogModel(dogRequest.getDogModel());
        String json = dogWorker.getDogData();
        System.out.println(TAG + " - " + json);
        Assert.assertNotEquals(json, "{ \"error\": \"Response unsuccessful\" }");
        Assert.assertNotEquals(json, "{ \"error\": \"Body's null, mind's full\" }");


    }

    @Test
    public void testRetrofit(){

        /*Controller controller = new Controller();
        controller.start();*/
        final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpZGRvZy1zZXJ2aWNlIiwic3ViIjoiNWMwNjdlYTY1YTk0MDE2ZWU3YTVjZjhiIiwiaWF0IjoxNTQzOTI5NTEwLCJleHAiOjE1NDUyMjU1MTB9.U0c-9mH4SsIbvtkouWGu2wSe6r5w6nVrKeimBvd5wvE";



        //set callback
        Callback<ListWrapper<DogViewModel>> dogCallback = new Callback<ListWrapper<DogViewModel>>() {
            @Override
            public void onResponse(Call<ListWrapper<DogViewModel>> call, Response<ListWrapper<DogViewModel>> response) {
                if (response.isSuccessful()) {
                    List<DogViewModel> data = new ArrayList<>();
                    data.addAll(response.body().dogs);
                    //set recyclerView adapter with new data
                    for (DogViewModel dog :
                            data) {
                        System.out.println(dog.getUrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListWrapper<DogViewModel>> call, Throwable t) {
                t.printStackTrace();
            }
        };


        //creating api
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = new Request.Builder()
                        .url(BASE_URL)
                        .header("Authorization", token)
                        .header("Content_Type","application/json")
                        .build();
                return chain.proceed(request);
            }
        }).build();



        IDDogAPI idDogAPI;
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        idDogAPI = retrofit.create(IDDogAPI.class);

        //request
        DogCategory dogCategory = new DogCategory("husky");
        idDogAPI.loadDog(dogCategory.toString()).enqueue(dogCallback);



    }


}
