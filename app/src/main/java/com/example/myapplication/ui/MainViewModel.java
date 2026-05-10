package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.database.repo.CategoryRepository;
import com.example.myapplication.model.Category;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";

    private final CategoryRepository categoryRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    // We expose LiveData to the UI so the Activity/Fragment stays ignorant of RxJava
    private final MutableLiveData<List<Category>> categoriesLiveDa = new MutableLiveData<>();

    public MainViewModel(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private void fetchCategories() {
        disposables.add(categoryRepository.getAllActiveCategories().subscribe(
                categories -> {
                    categoriesLiveDa.setValue(categories);
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
        return categoriesLiveDa;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}

