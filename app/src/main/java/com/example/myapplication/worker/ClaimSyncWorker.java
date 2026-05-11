package com.example.myapplication.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.model.Claim;

import java.io.IOException;
import java.util.List;

public class DataSyncWorker extends Worker {

    public DataSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // 1. Get database instance
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        // 2. Fetch all unsynced data
        List<Claim> unsyncedData = db.claimDao().getUnsyncedData();

        if (unsyncedData.isEmpty()) {
            return Result.success();
        }

        try {
            // 3. Send to server (e.g., using Retrofit)
            Response<Void> response = apiService.syncDataBatch(unsyncedData).execute();

            if (response.isSuccessful()) {
                // 4. If successful, mark as synced and update local DB
                for (UserData data : unsyncedData) {
                    data.isSynced = true;
                }
                db.userDataDao().updateData(unsyncedData);
                return Result.success();
            } else {
                // Server rejected it or had an error, retry later
                return Result.retry();
            }

        } catch (IOException e) {
            // Network failure during the request, retry later
            return Result.retry();
        }
    }
}