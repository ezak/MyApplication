package com.example.myapplication.settings;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class SettingsManager {
    private final RxDataStore<Preferences> dataStore;

    public static final Preferences.Key<String> USER_NAME = PreferencesKeys.stringKey("user_name");
    public static final Preferences.Key<String> KEY_LOCALE = PreferencesKeys.stringKey("selected_locale");

    public SettingsManager(Context context) {
        // Build the DataStore instance
        dataStore = new RxPreferenceDataStoreBuilder(context, "user_prefs").build();
    }


    public Flowable<String> getUserName() {
        return dataStore.data().map(prefs -> {
            String name = prefs.get(USER_NAME);
            return name != null ? name : "Default User";
        });
    }

    public Single<Preferences> updateUserName(String newName) {
        return dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(USER_NAME, newName);
            return Single.just(mutablePreferences);
        });
    }

    public Single<Preferences> updateLocale(String languageCode) {
        return dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(KEY_LOCALE, languageCode);
            return Single.just(mutablePreferences);
        });
    }

    public Flowable<String> getLocale() {
        return dataStore.data().map(prefs -> {
            String code = prefs.get(KEY_LOCALE);
            return (code != null) ? code : "en"; // Default to English
        });
    }
}