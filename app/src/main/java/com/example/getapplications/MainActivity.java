package com.example.getapplications;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.getapplications.utils.AppInfoFetcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            AppInfoFetcher appInfoFetcher = new AppInfoFetcher(MainActivity.this);
            appInfoFetcher.getAllInstalledApps();
        });
    }
}