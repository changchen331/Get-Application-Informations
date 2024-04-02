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

        // 获取外部存储的绝对路径
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        // 组合目标路径
        String filePath = absolutePath + "/" + context.getString(R.string.target_directory) + "/";

        // 获取PackageManager实例
        PackageManager packageManager = context.getPackageManager();
        try {
            List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
            // 过滤应用包信息（筛选需要的应用信息）
            for (PackageInfo aPackage : packages) {
                Matcher matcher = pattern.matcher(aPackage.applicationInfo.loadLabel(packageManager).toString());
                if (!matcher.find() && aPackage.applicationInfo.className != null) {
                    // 应用名称不包含‘.’ && 类名不为空
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
        } catch (Exception e) {
            // 处理获取应用信息时可能发生的异常
            Log.e("getAllInstalledApps", "Failed to get installed apps", e);
        }
    }
}
