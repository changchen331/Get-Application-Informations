package com.example.getapplications.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.example.getapplications.model.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获取应用信息
 */
public class AppInfoFetcher {
    private static final String TAG = "AppInfoFetcher";
    private final Context context;

    public AppInfoFetcher(Context context) {
        this.context = context;
    }

    @SuppressLint("QueryPermissionsNeeded")
    public List<AppInfo> getAllInstalledApps() {
        if (context == null) {
            // 提供一个空列表，因为没有有效的 context 来获取应用信息
            return Collections.emptyList();
        }

        // 用于存放应用数据
        List<AppInfo> appList = new ArrayList<>();
        // 获取 PackageManager 实例
        PackageManager packageManager = context.getPackageManager();

        // 筛选应用
        /* 方法 1 */
        // 正则表达式（匹配'.'）
//        Pattern pattern = Pattern.compile("\\.");
        // 获取所有应用包信息
//        List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
//        for (PackageInfo packageInfo : packages) {
//            try {
//                String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
//                String className = packageInfo.applicationInfo.className;
//                Drawable appIcon = packageInfo.applicationInfo.loadIcon(packageManager);
//                // 如果 AppName带有‘.’ 或者 没有应用类名 或者 没有App图标，pass
//                if (pattern.matcher(appName).find() || className == null || appIcon == null)
//                    continue;
//                // 获取信息
//                AppInfo appInfo = new AppInfo();
//                appInfo.setAppName(appName);
//                appInfo.setPackageName(packageInfo.packageName);
//                appInfo.setClassName(className);
//                appInfo.setVersionName(packageInfo.versionName);
//                appInfo.setAppIcon(appIcon);
//                appInfo.setActivities(packageInfo.activities);
//                appInfo.setSystemApp((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
//                // 过滤活动数量少于 20 的系统应用，pass
//                if (appInfo.isSystemApp() && appInfo.getActivities().length < 20) continue;
//                appList.add(appInfo);
//            } catch (Exception e) {
//                // 处理获取应用信息时可能发生的异常
//                Log.e(TAG, "Failed to get installed apps", e);
//            }
//        }

        /* 方法 2 */
        // 创建一个新的 Intent 对象，设置其动作为 Intent.ACTION_MAIN，表示主动作。
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        // 为 Intent 添加类别 Intent.CATEGORY_LAUNCHER，表示查找可以作为应用启动器的活动。
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        // 使用 PackageManager 的 queryIntentActivities 方法查询所有可以响应此 Intent 的活动。
        List<ResolveInfo> resolves = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolves) {
            AppInfo appInfo = new AppInfo();
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
            appInfo.setPackageName(activityInfo.packageName);
            appInfo.setAppIcon(activityInfo.loadIcon(packageManager));
            appInfo.setMainActivity(activityInfo);
            appList.add(appInfo);
        }

        Log.v(TAG, String.valueOf(appList.size()));
        return appList;
    }
}
