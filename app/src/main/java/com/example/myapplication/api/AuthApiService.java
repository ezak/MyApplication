package com.example.myapplication.api;

import com.example.myapplication.model.AuthApiResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthApiService {

    @GET("your/endpoint/path")
    Observable<AuthApiResponse> fetchData();

    @POST("your/endpoint/path")
    Observable<AuthApiResponse> login();

    @POST("your/endpoint/path")
    Observable<AuthApiResponse> register();



}
