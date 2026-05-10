package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.api.model.ApiResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceRepository {
    private static final String TAG = "ApiRepository";
    private final ApiService apiService;

    public ApiServiceRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Observable<ApiResponse> login() {
        return apiService.login();
    }

    public Observable<ApiResponse> register() {
        return apiService.register();
    }

    public Observable<ApiResponse> fetchData() {
        return apiService.fetchData();
    }

    public Observable<ApiResponse> postData() {
        return apiService.postData();
    }

}