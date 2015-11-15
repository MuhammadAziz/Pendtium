package com.pendtium.util;

/**
 * Created by ziddan on 11/16/15.
 */
public class Log {

    private static final String TAG = "Pendtium";

    public static void d(String msg) {
        android.util.Log.d(TAG, msg);
    }

}
