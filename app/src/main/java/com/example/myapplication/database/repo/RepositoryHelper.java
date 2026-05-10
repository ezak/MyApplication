package com.example.myapplication.database.repo;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myapplication.database.AppDatabase;

public class RepositoryHelper {
    private static final String TAG = "RepositoryHelper";


    private static CategoryRepository categoryRepository;
    private Context context;

    public RepositoryHelper(Context context) {

    }

    public synchronized static CategoryRepository getCategoryRepository(@NonNull Context appContext) {
        // if (categoryRepository == null) categoryRepository = new CategoryRepository(AppDatabase.getInstance(appContext));
        return categoryRepository;
    }
}
