package com.nuvoex.diesel.network;

import com.nuvoex.diesel.BuildConfig;
import com.nuvoex.diesel.core.Config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiModule {

    private static  String BASE_URL = null;
    private static Retrofit mRetrofit = null;
    private static ApiService mApiService = null;

    private synchronized static Retrofit getClient() {
        if (mRetrofit == null) {
            BASE_URL= Config.Companion.getSInstance().getBaseUrl();
            // Define the interceptor, add authentication headers
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().
                            addHeader("Accept", Config.Companion.getSInstance().getAcceptHeader()).
                            addHeader("Content-Type", "application/json").
                            build();
                    return chain.proceed(newRequest);
                }
            };


            // Add the interceptor to OkHttpClient
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(interceptor);

            //Add logging for debug builds
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.interceptors().add(httpLoggingInterceptor);
            }

            OkHttpClient client = builder.build();

            // Set the custom client when building adapter
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return mRetrofit;
    }

    public static ApiService getApiService() {
        return Injection.provideApiService();
    }

    public synchronized static ApiService getApiService(Class<? extends ApiService> apiServiceClass) {
        if (mApiService == null) {
            mApiService = getClient().create(apiServiceClass);
        }
        return mApiService;
    }
}