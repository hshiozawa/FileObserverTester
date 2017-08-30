package com.ripplex.fileobservertester;

import android.app.Application;
import android.util.Log;

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }
}
