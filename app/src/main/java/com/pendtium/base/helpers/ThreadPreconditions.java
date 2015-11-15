package com.pendtium.base.helpers;

import com.pendtium.BuildConfig;

import android.os.Looper;

/**
 * Created by ziddan on 5/10/14.
 */
public class ThreadPreconditions {
    public static void checkOnMainThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
}
