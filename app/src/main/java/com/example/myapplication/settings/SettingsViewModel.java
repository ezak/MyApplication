package com.example.myapplication.settings;

import android.util.Log;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.impl.model.Preference;

import com.example.myapplication.database.model.Category;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SettingsViewModel extends ViewModel {
    private static final String TAG = "SettingsViewModel";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<String> localeLive = new MutableLiveData<>();
    private final MutableLiveData<Preferences> preferencesLive = new MutableLiveData<>();
    private final SettingsRepository settingsRepository;

    public String currentLocale;

    public SettingsViewModel(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public void getUserName() {
        disposables.add(settingsRepository.getUserName().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                s -> {
                    Log.e(TAG, "getUserName: " + s.toLowerCase() );
                },
                throwable -> {
                    Log.e(TAG, "getUserName: " + throwable.getLocalizedMessage() );
                },
                () -> {
                    Log.e(TAG, "getUserName: completed "  );
                }));
    }

    public void updateUserName(String newUserName) {
        disposables.add(settingsRepository.updateUserName("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                preferences -> {
                    Log.e(TAG, "updateUserName: "  );
                },
                throwable -> {
                    Log.e(TAG, "updateUserName: " );
                }));
    }

    public void getLocale() {
        disposables.add(settingsRepository.getLocale().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                s -> {
                    localeLive.setValue(s);
                    Log.e(TAG, "getLocale: " + s );
                },
                throwable -> {
                    Log.e(TAG, "getLocale: " + throwable.getLocalizedMessage() );
                },
                ()-> {}));
    }

    public void updateLocale(String languageCode) {
        disposables.add(settingsRepository.updateLocale(languageCode).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                preferences -> {
                    Log.e(TAG, "updateLocale: " );
                    // getLocale();
                    preferencesLive.setValue(preferences);
                },
                throwable -> {
                    Log.e(TAG, "updateLocale: " + throwable.getLocalizedMessage() );
                }));
    }



    public MutableLiveData<String> getLocaleLive() {
        return localeLive;
    }

    public MutableLiveData<Preferences> getPreferencesLive() {
        return preferencesLive;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
