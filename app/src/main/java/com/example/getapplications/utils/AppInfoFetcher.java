package com.example.getapplications.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.getapplications.model.AppInfo;

import java.util.ArrayList;
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
    public List<AppInfo> getAllInstalledApps() {
        // 用于存放应用数据
        List<AppInfo> appList = new ArrayList<>();

        // 获取 PackageManager 实例
        PackageManager packageManager = context.getPackageManager();
        // 获取所有应用包信息
        List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);

        // 筛选应用
        // 正则表达式（匹配'.'）
        Pattern pattern = Pattern.compile("\\.");
        for (PackageInfo aPackage : packages) {
            Matcher matcher = pattern.matcher(aPackage.applicationInfo.loadLabel(packageManager).toString());
            if (matcher.find() || aPackage.applicationInfo.className == null ||
                    aPackage.applicationInfo.loadIcon(packageManager) == null ||
                    aPackage.activities == null) continue;

            // 是否为系统应用
            if ((aPackage.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                // 非系统应用（第三方应用）
                AppInfo appInfo = new AppInfo();
                appInfo.setAppName(aPackage.applicationInfo.loadLabel(packageManager).toString());
                appInfo.setPackageName(aPackage.packageName);
                appInfo.setClassName(aPackage.applicationInfo.className);
                appInfo.setVersionName(aPackage.versionName);
                appInfo.setAppIcon(aPackage.applicationInfo.loadIcon(packageManager));
                appInfo.setActivities(aPackage.activities);
                appInfo.setSystemApp((aPackage.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
                appList.add(appInfo);
//                Log.v("activities", appInfo.getAppName() + " -> " + aPackage.activities.length);
            } else {
                // 系统应用
                // 获取系统应用的所有活动（Activity）
                if (aPackage.activities.length < 20) continue;

                AppInfo appInfo = new AppInfo();
                appInfo.setAppName(aPackage.applicationInfo.loadLabel(packageManager).toString());
                appInfo.setPackageName(aPackage.packageName);
                appInfo.setClassName(aPackage.applicationInfo.className);
                appInfo.setVersionName(aPackage.versionName);
                appInfo.setAppIcon(aPackage.applicationInfo.loadIcon(packageManager));
                appInfo.setSystemApp((aPackage.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
                appList.add(appInfo);
//                Log.v("activities", appInfo.getAppName() + " -> " + aPackage.activities.length);
            }
        }
        Log.v("activities", String.valueOf(appList.size()));
        return appList;
    }
}
