package com.example.myapplication.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;

import com.example.myapplication.api.ApiServiceRepository;
import com.example.myapplication.database.DatabaseRepository;
import com.example.myapplication.security.JoseProvider;
import com.example.myapplication.security.JoseRepository;
import com.example.myapplication.security.KeyPairManager;
import com.example.myapplication.settings.SettingsManager;
import com.example.myapplication.settings.SettingsRepository;

public class RepositoryHelper {
    private static final String TAG = "RepositoryHelper";
    private static ApiServiceRepository apiRepository;
    private static DatabaseRepository databaseRepository;
    private static SettingsRepository settingsRepository;
    private static JoseRepository joseRepository;

    public RepositoryHelper() {
    }

    public synchronized static ApiServiceRepository getApiServiceRepository() {
        if (apiRepository == null)
            apiRepository = new ApiServiceRepository();

        return apiRepository;
    }

    public synchronized static DatabaseRepository getDatabaseRepository(@NonNull Application application, WorkManager workManager) {
        if (databaseRepository == null)
            databaseRepository = new DatabaseRepository(application, workManager);

        return databaseRepository;
    }

    public synchronized static SettingsRepository getSettingsRepository(SettingsManager settingsManager) {
        if (settingsRepository == null)
            settingsRepository = new SettingsRepository(settingsManager);

        return settingsRepository;
    }

    public synchronized static JoseRepository getJoseRepository() {
        if (joseRepository == null)
            joseRepository = new JoseRepository(new KeyPairManager(), new JoseProvider());

        return joseRepository;
    }
}
