package com.example.myapplication.api;

import com.example.myapplication.api.model.ApiResponse;
import com.example.myapplication.database.model.Claim;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("your/endpoint/path")
    Single<ApiResponse> login();

    @POST("your/endpoint/path")
    Single<ApiResponse> register();

    @POST("/posts")
    Single<ApiResponse> fetchData();

    @POST("your/endpoint/path")
    Single<ApiResponse> postData();

    @POST("/sync")
    Single<ApiResponse> syncClaims(@Body List<Claim> claimList);

}
