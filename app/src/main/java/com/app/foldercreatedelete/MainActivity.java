package com.app.foldercreatedelete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button createFolder, deleteFolder;
    File mydir;
    Context context;
    boolean success = true;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {

            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");

            } else {
                requestPermission();
            }
        }

        createFolder = findViewById(R.id.create);
        deleteFolder = findViewById(R.id.delete);

        context = getApplicationContext();

        createFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDir();
            }
        });

        deleteFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecursive(mydir);
            }
        });
    }

    public void createDir() {
        mydir = new File(Environment.getExternalStorageDirectory() + "/RAj");

        if (!mydir.exists()) {
            success = mydir.mkdir();
        }
        if (success) {
            Log.d("Yes", "Folder Created");
        } else {
            Log.d("No", "Folder Not Created");
        }
    }

    public void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        success = fileOrDirectory.delete();

        if (success) {
            Log.d("Delete", "Folder Delete");
        } else {
            Log.d("No Delete", "Folder Not Delete");
        }

    }

    private boolean checkPermission() {

        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
}
