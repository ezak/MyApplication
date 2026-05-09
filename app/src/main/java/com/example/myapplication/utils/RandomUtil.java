package com.example.myapplication.utils;

import java.util.Random;

public class RandomUtil {
    private static final String TAG = "RandomUtil";

    public static int getRandomInt(int bound) {
        return new Random().nextInt(bound);
    }


}
