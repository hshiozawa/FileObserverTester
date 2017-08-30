package com.ripplex.fileobservertester;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;

public class RuntimePermissionsChecker {
    public enum Result {
        GRANTED,
        DENIED,
        INVALID
    }

    private RuntimePermissionsChecker() {
    }

    private static final String[] STORAGE_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};

    public static boolean checkSelfStoragePermissions(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        return checkPermissionsImpl(context, STORAGE_PERMISSIONS);
    }

    public static boolean requestStoragePermissions(Activity activity, int requestCode) {
        if (activity == null) {
            throw new IllegalArgumentException("activity is null");
        }
        return requestPermissionsImpl(activity, STORAGE_PERMISSIONS, requestCode);
    }

    public static Result validateStoragePermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!isValidPermissionAndResults(permissions, grantResults, STORAGE_PERMISSIONS)) {
            return Result.INVALID;
        }

        return (grantResults[0] == PackageManager.PERMISSION_GRANTED) ? Result.GRANTED : Result.DENIED;
    }

    private static boolean checkPermissionsImpl(@NonNull Context context, @NonNull String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            int result = PermissionChecker.checkSelfPermission(context, permission);
            if (result != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private static boolean requestPermissionsImpl(@NonNull Activity activity, @NonNull String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        try {
            activity.requestPermissions(permissions, requestCode);
        } catch (Exception e) {
            return false;
        }

        return true;
    }


    private static boolean isValidPermissionAndResults(String[] actualPermissions, int[] actualGrantResults, @NonNull String[] expectedPermissions) {
        if (actualPermissions == null || actualGrantResults == null) {
            return false;
        }

        boolean sameLength =
                actualPermissions.length == expectedPermissions.length
                        && actualGrantResults.length == expectedPermissions.length;
        if (!sameLength) {
            return false;
        }

        for (int i = 0, size = expectedPermissions.length; i < size; i++) {
            if (!expectedPermissions[i].equals(actualPermissions[i])) {
                return false;
            }
        }
        return true;
    }
}

