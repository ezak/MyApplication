package com.example.myapplication.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.database.model.Category;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DatabaseViewModel extends ViewModel {
    private static final String TAG = "DatabaseViewModel";
    private final DatabaseRepository databaseRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    // We expose LiveData to the UI so the Activity/Fragment stays ignorant of RxJava
    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> subCategoriesLiveData = new MutableLiveData<>();

    private List<Category> subCategories = new ArrayList<>();
    private final List<String> currentTabTitles = new ArrayList<>();

    public DatabaseViewModel(@NonNull DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    private void fetchCategories() {
        disposables.add(databaseRepository.getAllActiveCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                categories -> {
                    categoriesLiveData.setValue(new ArrayList<>());
                    categoriesLiveData.setValue(categories);
                    Log.e(TAG, "fetchCategories:" );
                },
                error -> {
                    Log.e(TAG, "fetchCategories: "  + error);
                },
                () -> {
                    Log.e(TAG, "fetchCategories: done" );
                }
        ));
    }

    private void fetchSubCategories(int parent) {
        disposables.add(databaseRepository.getAllActiveSubCategories(parent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                categories -> {
                    subCategoriesLiveData.setValue(new ArrayList<>());
                    subCategoriesLiveData.setValue(categories);
                    Log.e(TAG, "fetchCategories:" );
                },
                error -> {
                    Log.e(TAG, "fetchCategories: "  + error);
                },
                () -> {
                    Log.e(TAG, "fetchCategories: done" );
                }
        ));
    }

    private void fetchSubCategories(String parent) {
        disposables.add(databaseRepository.getAllActiveSubCategories(parent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                categories -> {
                    subCategoriesLiveData.setValue(new ArrayList<>());
                    subCategoriesLiveData.setValue(categories);
                    Log.e(TAG, "fetchCategories:" );
                },
                error -> {
                    Log.e(TAG, "fetchCategories: "  + error);
                },
                () -> {
                    Log.e(TAG, "fetchCategories: done" );
                }
        ));
    }

    public LiveData<List<Category>> getAllCategoriesLive() {
        fetchCategories();
        return categoriesLiveData;
    }

    public LiveData<List<Category>> getAllSubCategoriesLive(int parent) {
        fetchSubCategories(parent);
        return subCategoriesLiveData;
    }

    public LiveData<List<Category>> getAllSubCategoriesLive(String parent) {
        fetchSubCategories(parent);
        return subCategoriesLiveData;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }


    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<String> getCurrentTabTitles() {
        return currentTabTitles;
    }

    public void addToCurrentTabTitles(String currentTabTitle) {
        this.currentTabTitles.add(currentTabTitle);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}