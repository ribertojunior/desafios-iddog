package com.casasw.iddog;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DogModel {
    String token;

    public DogModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

class DogViewModel {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
class ListWrapper<T> {
    List<T> dogs;
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
    private String mJSON;

    public String getmJSON() {
        return mJSON;
    }

    public void setmJSON(String mJSON) {
        this.mJSON = mJSON;
    }
}
