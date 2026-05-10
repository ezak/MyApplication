package com.example.myapplication.database.dao.repo;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myapplication.database.AppDatabase;

public class CategoryRepository {
    private static final String TAG = "CategoryRepository";
    private final AppDatabase db;
    public CategoryRepository(@NonNull AppDatabase db) {
        this.db = db;
    }

    public void getAllCategories() {

    }


}
