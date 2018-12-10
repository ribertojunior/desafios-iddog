package com.casasw.iddog;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.casasw.iddog.data.DogContract;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import okhttp3.Cache;
import okhttp3.OkHttpClient;


interface DogActivityInput {
    public void displayDogActivityData(DogViewModel viewModel);
}


public class DogActivity extends FragmentActivity
        implements DogActivityInput{

    public static String TAG = DogActivity.class.getSimpleName();
    DogInteractorInput output;
    DogRouter router;
    DogModel mDogModel;
    public static Picasso picassoWithCache;

    private String getBreed(){
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences
                .getString(getString(R.string.breed_key), getString(R.string.breed_key_default));
    }

    private void saveBreed(String breed){
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(getString(R.string.breed_key), breed).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);


        DogConfigurator.INSTANCE.configure(this);

        mDogModel = new DogModel(getIntent().getStringExtra("EXTRA_TOKEN"), getBreed());
        DogRequest aDogRequest =
                new DogRequest(mDogModel);
        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        Cache cache = new Cache(httpCacheDirectory, 15 * 1024 * 1024);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().cache(cache);
        picassoWithCache =
                new Picasso.Builder(this)
                        .downloader(new OkHttp3Downloader(okHttpClientBuilder.build())).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


       output.fetchDogActivityData(aDogRequest);



    }


    @Override
    public void displayDogActivityData(DogViewModel viewModel) {
        Log.e(TAG, "displayDogActivityData() called with: viewModel = [" + viewModel + "]");
        // Deal with the data
        String breed = viewModel.getCategory();
        ArrayList<String> list = viewModel.getList();
        Vector<ContentValues> contentValuesVector = new Vector<>(list.size());
        for (String str :
                list) {
            ContentValues cv = new ContentValues();
            cv.put(DogContract.DogEntry.COLUMN_DOG_BREED, breed);
            cv.put(DogContract.DogEntry.COLUMN_DOG_IMAGE_URL, str);
            contentValuesVector.add(cv);
        }
        if (contentValuesVector.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[contentValuesVector.size()];
            contentValuesVector.toArray(cvArray);
            getContentResolver().bulkInsert(DogContract.DogEntry.CONTENT_URI, cvArray);
        }


    }


}
