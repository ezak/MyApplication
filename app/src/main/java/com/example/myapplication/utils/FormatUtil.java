package com.example.myapplication.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtil {
    private static final String TAG = "FormatUtil";


    public static String formatNumber(int number) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault()); // Use device's locale
        return nf.format(number);
    }

    public static String formatNumber(long number) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault()); // Use device's locale
        return nf.format(number);
    }

    public static String formatNumber(double number) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault()); // Use device's locale
        return nf.format(number);
    }
}
