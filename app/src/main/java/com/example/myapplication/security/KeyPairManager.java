package com.example.myapplication.security;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.KeyPairGenerator;
import java.security.KeyStore;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class KeyPairManager {
    private static final String TAG = "KeyPairManager";
    public static final String ANDROID_KEYSTORE = "AndroidKeyStore";

    public KeyPairManager() {
    }

    public Single<Boolean> ensureKeyExists(String alias) {
        return getLoadedKeyStore().map(keyStore -> {
            if (!keyStore.containsAlias(alias)) {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE);
                keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(alias,
                        KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY |
                                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        // PKCS1 here so RS256 works!
                        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PSS, KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                        // Samsung note 5 does not support KeyProperties.DIGEST_SHA512
                        //.setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA1)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                        .build());
                keyPairGenerator.generateKeyPair();
            }
            return true;
        }).subscribeOn(Schedulers.io());
    }

    public Single<KeyStore> getLoadedKeyStore() {
        return Single.fromCallable(() -> {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
            keyStore.load(null);
            return keyStore;
        });
    }
}
