package com.example.myapplication.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.dao.ClaimDao;
import com.example.myapplication.database.model.Claim;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClaimSyncWorker extends RxWorker {
    private static final String TAG = "ClaimSyncWorker";
    private final ClaimDao dao;
    private final ApiService apiService;

    public ClaimSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.dao = AppDatabase.getInstance(context).claimDao();

        // Ideally, move Retrofit instantiation to a Singleton or Dependency Injection module
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }



    @NonNull
    @Override
    public Single<Result> createWork() {
        return dao.getUnSyncedClaims(false)
                .subscribeOn(Schedulers.io())
                .firstOrError() // Converts Flowable<List<Claim>> to Single<List<Claim>>
                .flatMap(unsyncedList -> {
                    if (unsyncedList.isEmpty()) {
                        return Single.just(Result.success());
                    }

                    return apiService.syncClaims(unsyncedList)
                            .flatMap(apiResponse -> {
                                if (apiResponse != null) {
                                    return markAsSynced(unsyncedList);
                                } else {
                                    return Single.just(Result.retry());
                                }
                            });
                })
                .onErrorReturn(t -> {
                    Log.e("ClaimSyncWorker", "Sync failed", t);
                    return Result.retry();
                });
    }

    /**
     * Helper to handle the success logic based on your API structure
     */
    private boolean isRequestSuccessful(Object response) {
        if (response instanceof retrofit2.Response) {
            return ((retrofit2.Response<?>) response).isSuccessful();
        }
        // Add your custom logic here if ApiResponse is a plain POJO
        // e.g., return ((ApiResponse) response).getStatus().equals("OK");
        return response != null;
    }


    /**
     * Updates the local list and saves to Database.
     * Wrapped in a Single to maintain the reactive chain.
     */
    private Single<Result> markAsSynced(List<Claim> claims) {
        for (Claim item : claims) {
            item.setIs_synced(true);
        }

        return dao.update(claims)
                .map(rowsAffected -> {
                    Log.d(TAG, "Successfully synced " + rowsAffected + " claims.");
                    return Result.success();
                })
                .onErrorReturnItem(Result.failure()); // DB failure is usually non-recoverable
    }
}