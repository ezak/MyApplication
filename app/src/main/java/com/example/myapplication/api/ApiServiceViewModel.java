package com.example.myapplication.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.model.ApiResponse;
import com.example.myapplication.model.DummyModel;
import com.example.myapplication.ui.RepositoryHelper;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ApiServiceViewModel extends ViewModel {
    private static final String TAG = "ApiServiceViewModel";

    // Container for all Rx subscriptions to prevent memory leaks
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Resource<List<DummyModel>>> dataState = new MutableLiveData<>();
    private final ApiServiceRepository apiServiceRepository;

    public ApiServiceViewModel() {
        apiServiceRepository = new ApiServiceRepository();
    }

    public ApiServiceViewModel(Integer x) {
        apiServiceRepository = new ApiServiceRepository();
    }

    public void login() {
        disposables.add(RepositoryHelper.getApiServiceRepository().login()
                .retryWhen(new ExponentialBackoffRetry(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authApiResponse -> {
                    Log.e(TAG, "fetchDataFromServer: " + authApiResponse );
                }, exception -> {
                    Log.e(TAG, "fetchDataFromServer: " + exception.getLocalizedMessage() );
                })
        );
    }

    public void register() {
        disposables.add(RepositoryHelper.getApiServiceRepository().register()
                .retryWhen(new ExponentialBackoffRetry(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authApiResponse -> {
                    Log.e(TAG, "fetchDataFromServer: " + authApiResponse );
                }, exception -> {
                    Log.e(TAG, "fetchDataFromServer: " + exception.getLocalizedMessage() );
                })
        );
    }

    public void fetchData() {
        dataState.setValue(Resource.loading());

        disposables.add(RepositoryHelper.getApiServiceRepository().fetchData().observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResponse -> {
                    dataState.setValue(Resource.success(apiResponse));
                }, exception -> {
                    dataState.setValue(Resource.error(exception.getLocalizedMessage()));
                }));
    }

    // Expose LiveData to the Activity
    public LiveData<Resource<List<DummyModel>>> getDataLive() {
        return dataState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
