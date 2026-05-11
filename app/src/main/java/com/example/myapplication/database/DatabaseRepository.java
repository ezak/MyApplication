package com.example.myapplication.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.myapplication.database.model.Category;
import com.example.myapplication.database.model.Claim;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DatabaseRepository {
    private static final String TAG = "DatabaseRepository";

    private final AppDatabase db;
    public DatabaseRepository(@NonNull Application application) {
        this.db = AppDatabase.getInstance(application);
    }

    private List<Category> filterActiveCategories(List<Category> categories) {
        return categories.stream()
                .filter(Category::isActive)
                .collect(Collectors.toList());
    }

    public Flowable<List<Category>> getAllActiveCategories() {
        return db.categoriesDao()
                .observeAllCategories()
                .map(this::filterActiveCategories)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<Category>> getAllActiveSubCategories(int parent) {
        return db.categoriesDao()
                .observeAllSubCategories(parent)
                .map(this::filterActiveCategories)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<Category>> getAllActiveSubCategories(String parent) {
        return db.categoriesDao()
                .observeAllSubCategories(parent)
                .map(this::filterActiveCategories)
                .subscribeOn(Schedulers.io());
    }

    /*
     * Claim related
     * */

    public Single<Long> insert(Claim claim) {
        return db.claimDao()
                .insert(claim)
                .subscribeOn(Schedulers.io());
    }

    public Single<Integer> update(Claim claim) {
        return db.claimDao()
                .update(claim)
                .subscribeOn(Schedulers.io());
    }

    public Single<Integer> delete(Claim claim) {
        return db.claimDao()
                .delete(claim)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<Claim>> getAllClaims() {
        return db.claimDao()
                .getAllClaims()
                .subscribeOn(Schedulers.io());
    }

    public Flowable<Claim> getClaimById(int id) {
        return db.claimDao()
                .getClaimById(id)
                .subscribeOn(Schedulers.io());
    }
}
