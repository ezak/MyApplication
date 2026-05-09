package com.example.myapplication.utils;

import com.google.gson.Gson;

public class JsonUtil {
    private static final String TAG = "JsonUtil";

    public static <T> String toJson(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String jsonStr, Class<T> c) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, c);
    }

}
