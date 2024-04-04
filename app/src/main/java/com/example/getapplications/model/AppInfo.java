package com.example.getapplications.model;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

/**
 * 应用信息
 */
public class AppInfo {
    private String appName; // 应用名称
    private String packageName; // 应用包名
    private Drawable appIcon; // 应用图标
    private ActivityInfo mainActivity; // 主界面（主活动）

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public ActivityInfo getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(ActivityInfo mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", appIcon=" + appIcon +
                ", mainActivity=" + mainActivity +
                '}';
    }
}
