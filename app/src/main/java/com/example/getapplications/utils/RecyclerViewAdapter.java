package com.example.getapplications.utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getapplications.R;
import com.example.getapplications.model.AppInfo;

import java.util.List;

/**
 * 用于在 RecyclerView 中显示应用信息列表的适配器
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<AppInfo> apps; // 存储应用信息列表

    public RecyclerViewAdapter(List<AppInfo> apps) {
        this.apps = apps;
    }

    /**
     * 创建 ViewHolder 实例，用于缓存 item 视图的引用
     *
     * @param parent   父视图组，通常是 RecyclerView 本身
     * @param viewType 视图类型，用于区分不同的 item 布局
     * @return ViewHolder 实例
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 通过 LayoutInflater 加载 item 布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * 将应用信息数据绑定到 item 视图上
     *
     * @param viewHolder ViewHolder 实例，持有对当前 item 视图的引用
     * @param position   当前 item 在数据集中的位置（索引）
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // 获取当前位置的应用信息
        AppInfo app = apps.get(position);
        // 设置应用信息
        String appName = app.getAppName();
        String packageName = app.getPackageName();
        String className = app.getClassName();
        String versionName = app.getVersionName();
        String isSystemApp = app.isSystemApp() ? "是" : "否";

        String appInfo = "名称：" + appName + "\n" +
                "包名：" + packageName + "\n" +
                "类名：" + className + "\n" +
                "版本号：" + versionName + "\n" +
                "是否为系统应用：" + isSystemApp;

        viewHolder.appInfo.setText(appInfo);
        // 设置应用图标
        viewHolder.appIcon.setImageDrawable(app.getAppIcon());
    }

    /**
     * 获取应用信息的总数
     *
     * @return 应用信息的数量
     */
    @Override
    public int getItemCount() {
        return apps != null ? apps.size() : 0;
    }

    /**
     * ViewHolder 内部类，用于缓存 item 视图中各个子视图的引用
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appInfo; // 应用信息
        ImageView appIcon; // 应用图标

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appInfo = itemView.findViewById(R.id.item_text);
            appIcon = itemView.findViewById(R.id.item_image);
        }
    }
}