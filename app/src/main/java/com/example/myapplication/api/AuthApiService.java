package com.example.myapplication.api;

import com.example.myapplication.model.AuthApiResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface AuthApiService {

    @GET("your/endpoint/path")
    Observable<AuthApiResponse> fetchData();
}
