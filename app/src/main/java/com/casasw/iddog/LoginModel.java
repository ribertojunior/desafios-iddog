package com.casasw.iddog;

public class LoginModel {
    private String user;


    public LoginModel(String user) {
        this.user = user;

    }

    public String getUser() {
        return user;
    }

}

class LoginViewModel {
    private String user;
    private String token;

    public LoginViewModel(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public LoginViewModel(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}

class LoginRequest {
    private LoginModel login;


    LoginRequest(LoginModel login) {
        this.login = login;
    }

    public LoginModel getLogin() {
        return login;
    }


}

class LoginResponse {
    private String loginJson;




    public String getLoginJson() {
        return loginJson;
    }

    public void setLoginJson(String loginJson) {
        this.loginJson = loginJson;
    }


}
