package com.ripplex.fileobservertester;

import android.os.FileObserver;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileObserverManager {
    private static final String TAG = FileObserverManager.class.getSimpleName();

    private static final int TARGET_EVENTS = FileObserver.CREATE | FileObserver.DELETE | FileObserver.MOVED_TO;
    // for debug
    //private static final int TARGET_EVENTS = FileObserver.ALL_EVENTS;

    private static final String[] TARGETS = new String[]{
            "/storage/emulated/0/DCIM/Camera",
    };

    private List<FileObserver> observers = new ArrayList<>();

    public void startWatch() {
        endWatch();

        Log.i(TAG, "startWatch");
        for (final String target : TARGETS) {
            Log.i(TAG, "-- " + target);

            FileObserver observer = new FileObserver(target, TARGET_EVENTS) {
                @Override
                public void onEvent(int event, String path) {
                    onFileEvent(event, path, target);
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

    private void onFileEvent(int event, String path, String basePath) {
        Log.i(TAG, "onFileEvent event=" + event + ",path=" + path);
        switch (event) {
            case FileObserver.CREATE:
                Log.i(TAG, "-- CREATE");
                checkFileExists(path, basePath, false);
                break;
            case FileObserver.MOVED_TO:
                Log.i(TAG, "-- MOVED_TO");
                checkFileExists(path, basePath, false);
                break;
            case FileObserver.DELETE:
                Log.i(TAG, "-- DELETE");
                checkFileExists(path, basePath, true);
                break;
            default:
                break;
        }
    }

    private static void checkFileExists(String path, String basePath, boolean loopUntilDeleted) {
        File file = new File(basePath + "/" + path);
        boolean exists;
        do {
            exists = file.exists();
            if (exists) {
                Log.w(TAG, "-- File exists. fullPath=" + file.getAbsolutePath());
            } else {
                Log.i(TAG, "-- File doesn't exists. fullPath=" + file.getAbsolutePath());
            }
        } while (loopUntilDeleted && exists);
    }
}
