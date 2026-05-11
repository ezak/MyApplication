package com.example.myapplication.settings;

import androidx.datastore.preferences.core.Preferences;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SettingsRepository {
    private static final String TAG = "SettingsRepository";

    private final SettingsManager settingsManager;

    public SettingsRepository(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public Flowable<String> getUserName() {
        return settingsManager.getUserName().subscribeOn(Schedulers.io());
    }

    public Single<Preferences> updateUserName(String newUserName) {
        return settingsManager.updateUserName(newUserName).subscribeOn(Schedulers.io());
    }

    public Single<Preferences> updateLocale(String languageCode) {
        return settingsManager.updateLocale(languageCode).subscribeOn(Schedulers.io());
    }

    public Flowable<String> getLocale() {
        return settingsManager.getLocale().subscribeOn(Schedulers.io());
    }
}
