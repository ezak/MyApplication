package com.example.myapplication.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.database.repo.CategoryRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final CategoryRepository categoryRepository;

    public MainViewModelFactory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(categoryRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}

