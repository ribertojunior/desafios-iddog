package com.casasw.iddog;

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
        DogResponse response = new DogResponse();
        DogWorker dogWorker = new DogWorker();
        dogWorker.setmDogModel(request.getDogModel());
        response.setmJSON(dogWorker.getDogData());


        output.presentDogActivityData(response);
    }
}
