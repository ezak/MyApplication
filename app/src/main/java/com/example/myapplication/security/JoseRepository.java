package com.example.myapplication.security;

import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.NoSuchElementException;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JoseRepository {
    private static final String TAG = "JoseRepository";
    private final KeyPairManager keyPairManager;
    private final JoseProvider joseProvider;

    public JoseRepository(KeyPairManager keyPairManager, JoseProvider joseProvider) {
        this.keyPairManager = keyPairManager;
        this.joseProvider = joseProvider;
    }

    private Single<JWSSigner> getSigner(KeyStore keyStore, String hwKeyAlias) {
        return Single.fromCallable(() -> {
            // Add a null/existence check similar to your verifier
            KeyStore.Entry entry = keyStore.getEntry(hwKeyAlias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                throw new NoSuchElementException("Private key entry not found for: " + hwKeyAlias);
            }

            PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
            return new RSASSASigner(privateKey);
        });
    }

    public Single<JWSVerifier> getVerifier(KeyStore keyStore, String hwKeyAlias) {
        return Single.fromCallable(() -> {
            // For verification, we only need the Public Key from the Certificate
            Certificate cert = keyStore.getCertificate(hwKeyAlias);
            if (cert == null) {
                throw new NoSuchElementException("No certificate found for alias: " + hwKeyAlias);
            }

            PublicKey publicKey = cert.getPublicKey();
            return new RSASSAVerifier((RSAPublicKey) publicKey);
        });
    }

    private Single<JWEEncrypter> getEncrypter(KeyStore keyStore, String hwKeyAlias) {
        return Single.fromCallable(() -> {
            Certificate cert = keyStore.getCertificate(hwKeyAlias);
            if (cert == null) {
                throw new NoSuchElementException("No certificate found for alias: " + hwKeyAlias);
            }

            PublicKey publicKey = cert.getPublicKey();
            if (!(publicKey instanceof RSAPublicKey)) {
                throw new IllegalStateException("Key alias '" + hwKeyAlias + "' is not an RSA key.");
            }

            // RSA-OAEP-256 requires an RSAPublicKey
            return new RSAEncrypter((RSAPublicKey) publicKey);
        });
    }

    private Single<JWEDecrypter> getDecrypter(KeyStore keyStore, String hwKeyAlias) {
        return Single.fromCallable(() -> {
            // Retrieve the PrivateKey entry
            KeyStore.Entry entry = keyStore.getEntry(hwKeyAlias, null);

            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                throw new NoSuchElementException("Private key not found or not accessible for alias: " + hwKeyAlias);
            }

            PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();

            // RSADecrypter handles RSA-OAEP algorithms
            return new RSADecrypter(privateKey);
        });
    }

    public Single<String> sign(String alias, String user) {
        return keyPairManager.ensureKeyExists(alias)
                .flatMap(exists -> keyPairManager.getLoadedKeyStore())
                .flatMap(keyStore -> getSigner(keyStore, alias))
                .flatMap(jwsSigner -> joseProvider.sign(Single.just(jwsSigner), user))
                .subscribeOn(Schedulers.computation());
    }

    public Single<Boolean> verify(String alias, String token) {
        return keyPairManager.ensureKeyExists(alias)
                .flatMap(exists -> keyPairManager.getLoadedKeyStore())
                .flatMap(keyStore -> getVerifier(keyStore, alias))
                .flatMap(jwsVerifier -> joseProvider.verify(Single.just(jwsVerifier), token))
                .subscribeOn(Schedulers.computation());
    }

    public Single<String> encrypt(String alias, String payload) {
        return keyPairManager.ensureKeyExists(alias)
                .flatMap(exists -> keyPairManager.getLoadedKeyStore())
                .flatMap(keyStore -> getEncrypter(keyStore, alias))
                .flatMap(jweEncrypter -> joseProvider.encrypt(payload, Single.just(jweEncrypter)))
                .subscribeOn(Schedulers.computation())
                .cache();
    }

    public Single<String> decrypt(String alias, String jweString) {
        return keyPairManager.ensureKeyExists(alias)
                .flatMap(exists -> keyPairManager.getLoadedKeyStore())
                .flatMap(keyStore -> getDecrypter(keyStore, alias))
                .flatMap(jweDecrypter -> joseProvider.decrypt(jweString, Single.just(jweDecrypter)))
                .subscribeOn(Schedulers.computation())
                .cache();
    }

    public Single<String> createCustomJWT(String alias, Map<String, Object> claims) {
        return keyPairManager.ensureKeyExists(alias)
                .flatMap(exists -> keyPairManager.getLoadedKeyStore())
                .flatMap(keyStore -> getSigner(keyStore, alias)) // Uses the private key
                .flatMap(jwsSigner -> joseProvider.generateJWT(claims, Single.just(jwsSigner)))
                .subscribeOn(Schedulers.computation());
    }

    public Single<Boolean> validateJWT(String alias, String token, String issuer) {
        return keyPairManager.ensureKeyExists(alias)
                .flatMap(exists -> keyPairManager.getLoadedKeyStore())
                .flatMap(keyStore -> getVerifier(keyStore, alias)) // Uses the public key
                .flatMap(jwsVerifier -> joseProvider.verifyJWT(token, issuer, Single.just(jwsVerifier)))
                .subscribeOn(Schedulers.computation());
    }

}
