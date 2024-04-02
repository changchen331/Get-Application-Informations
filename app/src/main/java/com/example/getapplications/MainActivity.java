package com.example.getapplications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.getapplications.utils.AppInfoFetcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Environment.isExternalStorageManager()) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                    return;
                }
                AppInfoFetcher appInfoFetcher = new AppInfoFetcher(MainActivity.this);
                appInfoFetcher.getAllInstalledApps();
            } else {
                AppInfoFetcher appInfoFetcher = new AppInfoFetcher(MainActivity.this);
                appInfoFetcher.getAllInstalledApps();
            }
            Toast.makeText(this, "应用信息导出完成", Toast.LENGTH_LONG).show();
        });
    }
}