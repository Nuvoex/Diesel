package com.nuvoex.diesel.core;



import com.nuvoex.diesel.model.LoginRequest;
import com.nuvoex.diesel.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RepositoryImpl implements Repository {

    private Call<LoginResponse> mLoginRequest;

    @Override
    public void login(String username, String password, final LoginCallback loginCallback) {
   /*     ApiService apiService =
                ApiModule.getApiService();
        mLoginRequest = apiService.login(new LoginRequest(username, password));
        mLoginRequest.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Timber.d("Login " + response.isSuccessful());
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    Timber.d("Login successful" + response.body().accessToken);
                    loginCallback.successful(loginResponse);
                } else {
                    if (response.code() == 401) {
                        loginCallback.wrongUsernamePassword();
                    } else {
                        loginCallback.failed();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginCallback.networkFailure();
                Timber.d("Login failed ");
            }
        });*/
    }

    @Override
    public void cancelLoginRequest() {
        if (mLoginRequest != null) {
            mLoginRequest.cancel();
        }
    }

    @Override
    public void saveLoginDetails(LoginResponse loginResponse) {
      /*  JacksonPrefs.getInstance().setIsUserLoggedIn(true);
        JacksonPrefs.getInstance().setToken(loginResponse.accessToken);
        JacksonPrefs.getInstance().setUserInfo(loginResponse.userInfo);*/
    }
}
