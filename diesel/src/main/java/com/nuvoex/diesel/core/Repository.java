package com.nuvoex.diesel.core;


import com.nuvoex.diesel.model.LoginResponse;

public interface Repository {

    interface LoginCallback {

        void successful(LoginResponse loginResponse);

        void wrongUsernamePassword();

        void failed();

        void networkFailure();
    }

    void login(String username, String password, LoginCallback loginCallback);

    void cancelLoginRequest();

    void saveLoginDetails(LoginResponse loginResponse);

}