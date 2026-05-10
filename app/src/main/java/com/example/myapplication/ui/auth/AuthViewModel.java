package com.example.myapplication.ui.auth;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.AuthApiService;
import com.example.myapplication.api.ExponentialBackoffRetry;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    // Container for all Rx subscriptions to prevent memory leaks
    private final CompositeDisposable disposables = new CompositeDisposable();
    private AuthApiService authApiService;

    public AuthViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.example.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        authApiService = retrofit.create(AuthApiService.class);
    }

    private void fetchDataFromServer() {
        disposables.add(authApiService.fetchData()
                .retryWhen(new ExponentialBackoffRetry(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authApiResponse -> {
                    Log.e(TAG, "fetchDataFromServer: " + authApiResponse );
                }, exception -> {
                    Log.e(TAG, "fetchDataFromServer: " + exception.getLocalizedMessage() );
                }, () -> {
                    Log.e(TAG, "fetchDataFromServer: "  );
                })
        );
    }

    public void login() {
        disposables.add(authApiService.fetchData()
                .retryWhen(new ExponentialBackoffRetry(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authApiResponse -> {
                    Log.e(TAG, "fetchDataFromServer: " + authApiResponse );
                }, exception -> {
                    Log.e(TAG, "fetchDataFromServer: " + exception.getLocalizedMessage() );
                }, () -> {
                    Log.e(TAG, "fetchDataFromServer: "  );
                })
        );
    }

    public void register() {

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
