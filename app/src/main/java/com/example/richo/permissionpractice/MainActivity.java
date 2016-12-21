package com.example.richo.permissionpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton cameraActionButton, fileActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraActionButton = (FloatingActionButton) findViewById(R.id.camera_item);
        fileActionButton = (FloatingActionButton) findViewById(R.id.file_item);

        cameraActionButton.setOnClickListener(clickListener);
        fileActionButton.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.camera_item:
                    Log.d("Tag", "Camera button pressed!");
                    break;
                case R.id.file_item:
                    Log.d("Tag", "File button pressed!");
                    break;
                default:
                    break;
            }
        }
    };
}
