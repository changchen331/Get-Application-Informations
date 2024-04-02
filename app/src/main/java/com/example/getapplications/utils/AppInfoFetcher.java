package com.example.getapplications.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.example.getapplications.R;
import com.example.getapplications.model.AppInfo;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取应用信息
 */
public class AppInfoFetcher {
    private final Context context;

    public AppInfoFetcher(Context context) {
        this.context = context;
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void getAllInstalledApps() {
        // 正则表达式（匹配'.'）
        Pattern pattern = Pattern.compile("\\.");

        // 获取外部存储的根目录
        File externalStorageDir = Environment.getExternalStorageDirectory();
        // 获取外部存储的绝对路径
        String absolutePath = externalStorageDir.getAbsolutePath();
        // 组合目标路径
        String filePath = absolutePath + "/" + context.getString(R.string.target_directory);

        // 获取PackageManager实例
        PackageManager packageManager = context.getPackageManager();
        try {
            // 检查外部存储是否可用
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                // 外部存储已挂载，可以进行读写操作
                // 获取所有应用包信息
                List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);

                // 过滤应用包信息（筛选需要的应用信息）
                for (PackageInfo aPackage : packages) {
                    if (aPackage != null) {
                        Matcher matcher = pattern.matcher(aPackage.applicationInfo.loadLabel(packageManager).toString());
                        if (!matcher.find() && aPackage.applicationInfo.className != null && aPackage.applicationInfo.loadIcon(packageManager) != null) {
                            // 应用名称不包含‘.’ && 类名不为空 && 应用图标不为空
                            // 获取信息
                            AppInfo appInfo = new AppInfo();
                            appInfo.setAppName(aPackage.applicationInfo.loadLabel(packageManager).toString());
                            appInfo.setPackageName(aPackage.packageName);
                            appInfo.setClassName(aPackage.applicationInfo.className);
                            appInfo.setVersionName(aPackage.versionName);
                            appInfo.setAppIcon(aPackage.applicationInfo.loadIcon(packageManager));
                            appInfo.setSystemApp((aPackage.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);

                            if (packages.indexOf(aPackage) == 0) {
                                // 覆写
                                FileUtils.writeTxtToFile(appInfo.toString(), filePath, context.getString(R.string.file_name), Boolean.FALSE);
                            } else {
                                // 追加
                                FileUtils.writeTxtToFile(appInfo.toString(), filePath, context.getString(R.string.file_name), Boolean.TRUE);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // 处理获取应用信息时可能发生的异常
            Log.e("getAllInstalledApps", "Failed to get installed apps", e);
        }
    }
}
