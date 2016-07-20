package com.nuvoex.diesel.network;


import com.nuvoex.diesel.model.LoginRequest;
import com.nuvoex.diesel.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {

    class Urls {
        public static final String LOGIN = "oauth/token";
    }

    @POST(Urls.LOGIN)
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}