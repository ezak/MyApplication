package com.example.myapplication.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    private static final String TAG = "ValidationUtil";

    private static final String REGEX_ALPHA_NUMERIC = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$";

    public static boolean isAlphaNumeric(String txt) {
        Pattern pattern = Pattern.compile(REGEX_ALPHA_NUMERIC);
        Matcher matcher = pattern.matcher(txt);
        return matcher.matches();
    }

}
