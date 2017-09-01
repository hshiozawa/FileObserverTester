package com.ripplex.fileobservertester;

import android.app.Application;
import android.util.Log;

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();

    private FileObserverManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");

        manager = new FileObserverManager();
    }

    public FileObserverManager getFileObserverManager() {
        return manager;
    }
}
