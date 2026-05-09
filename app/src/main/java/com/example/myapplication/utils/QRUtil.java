package com.example.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

public class QRUtil {
    private static final String TAG = "QRUtil";

/*    public static Bitmap strToQRC(String data, int xy) {
        BitMatrix result;

        try {
            result = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, xy, xy, null);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        final int[] pixels = new int[xy * xy];

        for (int y = 0; y < xy; y++) {
            final int offset = y * xy;
            for (int x = 0; x < xy; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.TRANSPARENT;
            }
        }

        final Bitmap bitmap = Bitmap.createBitmap(xy, xy, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, xy, 0, 0, xy, xy);

        return bitmap;
    }*/
}
