package com.qsoft.components.gallery.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * User: thinhdd
 * Date: 1/24/14
 * Time: 4:10 PM
 */
public class ImageUtils
{
    private static final int TIMEOUT_CONNECTION = 5000;//5sec
    private static final int TIMEOUT_SOCKET = 30000;//30sec

    public static Bitmap getResizeBitmap(Bitmap bm, float newWidth, float newHeight)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        return Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, true);
    }

    public static Map<String, Integer> getSizeImage(Context context, int ResourceId)
    {
        Map<String, Integer> sizeMap = new HashMap<String, Integer>();
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), ResourceId, dimensions);
        sizeMap.put("HEIGHT", dimensions.outHeight);
        sizeMap.put("WIDTH", dimensions.outWidth);
        return sizeMap;
    }
}
