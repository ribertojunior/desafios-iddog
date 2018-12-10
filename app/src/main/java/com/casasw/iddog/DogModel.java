package com.casasw.iddog;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DogModel {
    String token;
    String breed;

    public DogModel(String token, String breed) {
        this.token = token;
        this.breed = breed;
    }

    public String getToken() {
        return token;
    }

    public String getBreed() {
        return breed;
    }
}

class DogViewModel {
    private String category;
    private ArrayList<String> list;

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

class DogCategory {
    @SerializedName("category")
    private String category;

    public DogCategory() {
    }

    public DogCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }

}

class DogRequest {
    private DogModel dogModel;

    public DogRequest(DogModel dogModel) {
        this.dogModel = dogModel;
    }

    public DogModel getDogModel() {
        return dogModel;
    }
}

class DogResponse {
    private DogViewModel dogViewModel;

    public DogResponse(DogViewModel dogViewModel) {
        this.dogViewModel = dogViewModel;
    }

    public DogViewModel getDogViewModel() {
        return dogViewModel;
    }
}
