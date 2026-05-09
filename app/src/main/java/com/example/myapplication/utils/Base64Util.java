package com.example.myapplication.utils;

import android.util.Base64;

public class Base64Util {
    private static final String TAG = "Base64Util";

    public static String toBase64(String data) {
        return new String(Base64.encode(data.getBytes(), Base64.URL_SAFE));
    }

    public static String fromBase64(String data) {
        return new String(Base64.decode(data.getBytes(), Base64.URL_SAFE));
    }
}
