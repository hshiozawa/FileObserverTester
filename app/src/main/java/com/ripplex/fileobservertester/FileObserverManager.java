package com.ripplex.fileobservertester;

import android.os.FileObserver;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FileObserverManager {
    private static final String TAG = FileObserverManager.class.getSimpleName();

    private static final int TARGET_EVENTS = FileObserver.DELETE;
    // for debug
    // private static final int TARGET_EVENTS = FileObserver.ALL_EVENTS;

    private static final String[] TARGETS = new String[]{
            "/storage/emulated/0/DCIM/Camera/"
    };

    private List<FileObserver> observers = new ArrayList<>();

    public void startWatch() {
        endWatch();

        Log.i(TAG, "startWatch");
        for (String target : TARGETS) {
            Log.i(TAG, "-- " + target);

            FileObserver observer = new FileObserver(target, TARGET_EVENTS) {
                @Override
                public void onEvent(int event, String path) {
                    Log.i(TAG, "onEvent event=" + event + ",path=" + path);
                }
            };
            observer.startWatching();
            observers.add(observer);
        }
    }

    public void endWatch() {
        Log.i(TAG, "endWatch");

        for (FileObserver observer : observers) {
            observer.stopWatching();
        }

        observers.clear();
    }
}
