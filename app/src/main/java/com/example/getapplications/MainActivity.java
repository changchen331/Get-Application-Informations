package com.example.getapplications;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getapplications.model.AppInfo;
import com.example.getapplications.utils.AppInfoFetcher;
import com.example.getapplications.utils.FileUtils;
import com.example.getapplications.utils.RecyclerViewAdapter;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<AppInfo> apps; // 应用信息列表
    Boolean isButtonPressed = Boolean.FALSE; // 按钮是否被点击

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取应用数据
        apps = getApplicationInformation(MainActivity.this);

        // 应用信息滚动弹窗
        RecyclerView recyclerView = findViewById(R.id.application_information);

        // 获取应用数据按钮
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            if (!isButtonPressed) {
                // 初始化滚动弹窗
                initRecyclerView(recyclerView);

                // 显示滚动弹窗
                recyclerView.setVisibility(View.VISIBLE);

                // 写入文件（仅安卓系统）
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivity(intent);
                        return;
                    }
                }
                writeIntoFile(MainActivity.this);

                Toast.makeText(this, "应用信息导出完成", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "应用信息导出完成", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 获取所有应用信息
     *
     * @param context 指定环境
     * @return 应用信息列表，或者在无法获取时返回空列表
     */
    private List<AppInfo> getApplicationInformation(Context context) {
        // 检查 context 是否为 null
        if (context == null) {
            // 提供一个空列表，因为没有有效的 context 来获取应用信息
            return Collections.emptyList();
        }

        // 创建 AppInfoFetcher 实例
        AppInfoFetcher appInfoFetcher = new AppInfoFetcher(context);
        try {
            return appInfoFetcher.getAllInstalledApps();
        } catch (Exception e) {
            // 处理获取应用信息时可能发生的异常
            Log.e("GetApplicationInformation", "Failed to get installed apps", e);
            return Collections.emptyList(); // 返回空列表
        }
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        // 应用选择视图
        if (recyclerView == null) {
            // 处理找不到 RecyclerView 控件的情况
            return;
        }

        // 创建 GridLayoutManager 实例
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        // 设置 recyclerView 为竖向滚动
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        // 将 gridLayoutManager 设置为 recyclerView 的布局管理器
        recyclerView.setLayoutManager(gridLayoutManager);

        // 创建 RecyclerViewAdapter 实例
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(apps);
        // 将 recyclerViewAdapter 设置为 recyclerView 的适配器
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void writeIntoFile(Context context) {
        // 获取外部存储的绝对路径
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        // 组合目标路径
        String filePath = absolutePath + "/" + context.getString(R.string.target_directory) + "/";

        for (AppInfo app : apps) {
            if (apps.indexOf(app) == 0) {
                // 覆写
                FileUtils.writeTxtToFile(app.toString(), filePath, context.getString(R.string.file_name), Boolean.FALSE);
            } else {
                // 追加
                FileUtils.writeTxtToFile(app.toString(), filePath, context.getString(R.string.file_name), Boolean.TRUE);
            }
        }
    }
}