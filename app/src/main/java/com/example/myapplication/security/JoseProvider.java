package com.example.myapplication.security;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Date;
import java.util.Map;

public class JoseProvider {
    private static final String TAG = "JoseProvider";

    public Single<String> sign(Single<? extends JWSSigner> signerSingle, String user) {
        return signerSingle.flatMap(signer -> Single.fromCallable(() -> {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user)
                    .issuer("https://openimis.org")
                    .expirationTime(new Date(new Date().getTime() + 60 * 1000 * 30)) // 30 mins
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);

            signedJWT.sign(signer);
            return signedJWT.serialize();

        })).subscribeOn(Schedulers.computation()); // Signing is CPU intensive
    }

    public Single<Boolean> verify(Single<? extends JWSVerifier> verifierSingle, String token) {
        return verifierSingle.flatMap(verifier -> Single.fromCallable(() -> {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.verify(verifier);
        })).subscribeOn(Schedulers.computation());
    }

    public Single<String> encrypt(String payload, Single<? extends JWEEncrypter> encrypterSingle) {
        return encrypterSingle.flatMap(encrypter -> Single.fromCallable(() -> {
            // Build JWE header (e.g., RSA-OAEP-256 with AES/GCM)
            // JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM);
            // Change RSA_OAEP_256 to RSA_OAEP
            JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP, EncryptionMethod.A256GCM);
            Payload jwePayload = new Payload(payload);

            JWEObject jweObject = new JWEObject(header, jwePayload);
            jweObject.encrypt(encrypter);

            return jweObject.serialize();
        })).subscribeOn(Schedulers.computation());
    }

    public Single<String> decrypt(String jweString, Single<? extends JWEDecrypter> decrypterSingle) {
        return decrypterSingle.flatMap(decrypter -> Single.fromCallable(() -> {
            JWEObject jweObject = JWEObject.parse(jweString);
            jweObject.decrypt(decrypter);

            return jweObject.getPayload().toString();
        })).subscribeOn(Schedulers.computation());
    }

    public Single<String> generateJWT(Map<String, Object> claims, Single<? extends JWSSigner> signerSingle) {
        return signerSingle.flatMap(signer -> Single.fromCallable(() -> {
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }

            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);

            signedJWT.sign(signer);
            return signedJWT.serialize();
        })).subscribeOn(Schedulers.computation());
    }

    public Single<Boolean> verifyJWT(String token, String issuer, Single<? extends JWSVerifier> verifierSingle) {
        return verifierSingle.flatMap(verifier -> Single.fromCallable(() -> {
            SignedJWT signedJWT = SignedJWT.parse(token);

            // 1. Verify Signature
            if (!signedJWT.verify(verifier)) return false;

            // 2. Verify Claims (Expiration, Issuer, etc.)
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date now = new Date();

            boolean isNotExpired = claims.getExpirationTime() == null || now.before(claims.getExpirationTime());
            boolean isCorrectIssuer = claims.getIssuer().equals(issuer);

            return isNotExpired && isCorrectIssuer;
        })).subscribeOn(Schedulers.computation());
    }
}