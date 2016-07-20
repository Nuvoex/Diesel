package com.nuvoex.diesel.network;

public class Injection {

    public static ApiService provideApiService() {
        return ApiModule.getApiService(ApiService.class);
    }
}