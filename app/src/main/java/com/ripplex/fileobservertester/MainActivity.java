package com.ripplex.fileobservertester;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!RuntimePermissionsChecker.checkSelfStoragePermissions(getApplicationContext())) {
            RuntimePermissionsChecker.requestStoragePermissions(this, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            RuntimePermissionsChecker.Result result = RuntimePermissionsChecker.validateStoragePermissionsResult(permissions, grantResults);
            switch (result) {
                case GRANTED:
                    break;
                case DENIED:
                case INVALID:
                    Toast.makeText(this, R.string.no_permission, Toast.LENGTH_LONG).show();
                    break;
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
