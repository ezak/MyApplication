package com.example.myapplication.security;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JoseViewModel extends ViewModel {
    private static final String TAG = "JoseViewModel";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final JoseRepository joseRepository;

    public JoseViewModel(JoseRepository joseRepository) {
        this.joseRepository = joseRepository;
    }

    public void sign(String hwKeyAlias, String user) {
        disposables.add(joseRepository.sign(hwKeyAlias, user).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                s -> {
                    Log.e(TAG, "sign: " + s);
                },
                throwable -> {
                    Log.e(TAG, "sign: " + throwable.getLocalizedMessage());
                }));
    }

    public void verify(String hwKeyAlias, String token) {
        disposables.add(joseRepository.verify(hwKeyAlias, token).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                aBoolean -> {
                    Log.e(TAG, "verify: " + aBoolean);
                },
                throwable -> {
                    Log.e(TAG, "verify: " + throwable);
                }));
    }

    public void encrypt(String alias, String payload) {
        disposables.add(joseRepository.encrypt(alias, payload).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                s -> {
                    Log.e(TAG, "encrypt: " + s);
                },
                throwable -> {
                    Log.e(TAG, "encrypt: " + throwable.getLocalizedMessage());
                }));
    }

    public void decrypt(String alias, String payload) {
        disposables.add(joseRepository.decrypt(alias, payload).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                s -> {
                    Log.e(TAG, "decrypt: " + s);
                }, throwable -> {
                    Log.e(TAG, "decrypt: " + throwable.getLocalizedMessage());
                }));
    }

    public void createJWT(String alias, Map<String, Object> claim) {
        disposables.add(joseRepository.createCustomJWT(alias, claim).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                s -> {
                    Log.e(TAG, "createJWT: token " + s);
                },
                throwable -> {
                    Log.e(TAG, "createJWT: " + throwable.getLocalizedMessage());
                }));
    }

    public void verifyJWT(String alias, String token, String issuer) {
        disposables.add(joseRepository.validateJWT(alias, token, issuer).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                aBoolean -> {
                    Log.e(TAG, "verifyJWT: " + aBoolean);
                },
                throwable -> {
                    Log.e(TAG, "verifyJWT: " + throwable.getLocalizedMessage());
                }));
    }
}
