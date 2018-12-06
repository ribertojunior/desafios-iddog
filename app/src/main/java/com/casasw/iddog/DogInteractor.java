package com.casasw.iddog;

import android.os.AsyncTask;

import com.google.gson.Gson;

interface DogInteractorInput {
    public void fetchDogActivityData(DogRequest request);
}


public class DogInteractor implements DogInteractorInput {

    public static String TAG = DogInteractor.class.getSimpleName();
    public DogPresenterInput output;
    public DogWorkerInput aDogWorkerInput;

    public DogWorkerInput getDogActivityWorkerInput() {
        if (aDogWorkerInput == null) return new DogWorker();
        return aDogWorkerInput;
    }

    public void setDogActivityWorkerInput(DogWorkerInput aDogWorkerInput) {
        this.aDogWorkerInput = aDogWorkerInput;
    }

    @Override
    public void fetchDogActivityData(DogRequest request) {
        aDogWorkerInput = getDogActivityWorkerInput();
        DogDataTask task = new DogDataTask();
        task.execute(request.getDogModel().getToken(), request.getDogModel().getBreed());

    }

    private class DogDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            DogViewModel dogViewModel;
            dogViewModel = gson.fromJson(s, DogViewModel.class);
            DogResponse response = new DogResponse(dogViewModel);

            output.presentDogActivityData(response);
        }

        @Override
        protected String doInBackground(String... strings) {
            DogWorker dogWorker = new DogWorker();
            dogWorker.setmDogModel(new DogModel(strings[0], strings[1]));
            return dogWorker.getDogData();
        }
    }
}
