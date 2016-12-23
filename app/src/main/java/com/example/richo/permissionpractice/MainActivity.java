package com.example.richo.permissionpractice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 3;

    private FloatingActionMenu mMenu;
    private FloatingActionButton cameraActionButton, fileActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMenu = (FloatingActionMenu) findViewById(R.id.menu);
        cameraActionButton = (FloatingActionButton) findViewById(R.id.camera_item);
        fileActionButton = (FloatingActionButton) findViewById(R.id.file_item);

        cameraActionButton.setOnClickListener(clickListener);
        fileActionButton.setOnClickListener(clickListener);

        requestLocationPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            if(info.requestedPermissions != null) {
                for(String permission : info.requestedPermissions) {
                    Log.d(TAG, permission + " granted: " + isPermissionGranted(permission));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    showDeniedSnackBar();
                }
                return;
            case MY_PERMISSIONS_REQUEST_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    showDeniedSnackBar();
                }
                return;
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    showLocationBasedPhotos();
                } else {
                    
                    Log.i(TAG, "LOCATION permission was NOT granted.");
                    showDeniedSnackBar();
                    showLocationUnauthorized();
                }
                return;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.camera_item:
                    requestCameraPermission();
                    break;
                case R.id.file_item:
                    requestStoragePermission();
                    break;
                default:
                    break;
            }

            mMenu.close(true);
        }
    };

    private boolean isPermissionGranted(String permission) {
        int permissionState = ContextCompat.checkSelfPermission(this, permission);
        return (permissionState == PackageManager.PERMISSION_GRANTED);
    }

    private void requestCameraPermission() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Log.d(TAG, "Explanation needed!");
                showDeniedSnackBar();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    private void requestStoragePermission() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission_group.STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission_group.STORAGE)) {
                Log.d(TAG, "Explanation needed!");
                showDeniedSnackBar();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
        }
    }

    private void requestLocationPermission() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Log.d(TAG, "LOCATION Permission explanation needed!");
                showDeniedSnackBar();
                showLocationUnauthorized();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    private void showDeniedSnackBar() {
        Snackbar snackbar = Snackbar
                .make(this.findViewById(android.R.id.content), R.string.permissions_not_granted, Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
        snackbar.show();
    }

    private void showLocationUnauthorized() {
        UserMessageFragment fragment = new UserMessageFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_panel, fragment)
                .commit();
    }

    private void showLocationBasedPhotos() {
        LocalPhotosFragment fragment = new LocalPhotosFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_panel, fragment)
                .commit();
    }
}
