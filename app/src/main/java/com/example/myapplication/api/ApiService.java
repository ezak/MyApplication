package com.example.myapplication.api;

import com.example.myapplication.api.model.ApiResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.POST;

public interface ApiService {

    @POST("your/endpoint/path")
    Observable<ApiResponse> login();

    @POST("your/endpoint/path")
    Observable<ApiResponse> register();

    @POST("/posts")
    Observable<ApiResponse> fetchData();

    @POST("your/endpoint/path")
    Observable<ApiResponse> postData();
}
