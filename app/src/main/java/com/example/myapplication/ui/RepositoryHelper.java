package com.example.myapplication.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myapplication.api.ApiServiceRepository;
import com.example.myapplication.database.DatabaseRepository;

public class RepositoryHelper {

    private static ApiServiceRepository apiRepository;
    private static DatabaseRepository databaseRepository;

    public RepositoryHelper() {
    }

    public synchronized static ApiServiceRepository getApiServiceRepository() {
        if (apiRepository == null)
            apiRepository = new ApiServiceRepository();

        return apiRepository;
    }


    public synchronized static DatabaseRepository getDatabaseRepository(@NonNull Application application) {
        if (databaseRepository == null)
            databaseRepository = new DatabaseRepository(application);

        return databaseRepository;
    }
}
