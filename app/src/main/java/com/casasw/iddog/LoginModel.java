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
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    private LoginViewModel loginViewModel;


    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public void setLoginViewModel(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }
}

class User {
    private String email;
    private String token;


    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}