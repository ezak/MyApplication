package com.example.myapplication.api;

import android.util.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import java.util.concurrent.TimeUnit;

public class ExponentialBackoffRetry implements Function<Observable<Throwable>, ObservableSource<?>> {
    private static final String TAG = "ExponentialBackoffRetry";
    private final int maxRetries;
    private final int baseDelaySeconds;

    public ExponentialBackoffRetry(int maxRetries, int baseDelaySeconds) {
        this.maxRetries = maxRetries;
        this.baseDelaySeconds = baseDelaySeconds;
    }

    @Override
    public ObservableSource<?> apply(Observable<Throwable> errors) {
        return errors.zipWith(Observable.range(1, maxRetries + 1), (error, attempt) -> {
            if (attempt > maxRetries) {
                throw error;
            }
            return attempt;
        }).flatMap(attempt -> {
            long delay = (long) Math.pow(baseDelaySeconds, attempt);
            Log.w(TAG, "API request failed. Retrying in " + delay + " seconds (Attempt " + attempt + " of " + maxRetries + ").");

            return Observable.timer(delay, TimeUnit.SECONDS);
        });
    }
}
