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
 * 负责将数据集合中的数据显示在 RecyclerView 上，并处理数据的操作，同时支持点击事件回调
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<AppInfo> apps; // 存储应用信息列表
    private int position = 0; // 记录当前选中 item 的位置
    private OnItemClickListener onItemClickListener; // 定义点击事件的回调接口

    public RecyclerViewAdapter(List<AppInfo> apps) {
        this.apps = apps;
    }

    /**
     * 设置点击事件回调接口
     *
     * @param onItemClickListener 点击事件的回调接口
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        String mainActivity = app.getMainActivity().name;
        String appInfo = "名称：" + appName + "\n" + "包名：" + packageName + "\n" + "主界面：" + mainActivity;
        viewHolder.appInfo.setText(appInfo);
        // 设置应用图标
        viewHolder.appIcon.setImageDrawable(app.getAppIcon());

        // 设置 item 视图的选中状态
        viewHolder.itemView.setSelected(position == this.position);
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
     * 定义点击事件的回调接口
     */
    public interface OnItemClickListener {
        /**
         * 处理 item 的点击事件
         *
         * @param itemView 被点击的 item 视图
         * @param position 被点击 item 的位置
         */
        void onItemClick(View itemView, int position);
    }

    /**
     * ViewHolder 内部类，用于缓存 item 视图中各个子视图的引用
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView appInfo; // 应用信息
        ImageView appIcon; // 应用图标

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appInfo = itemView.findViewById(R.id.item_text);
            appIcon = itemView.findViewById(R.id.item_image);

            // 设置点击事件
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    if (getAdapterPosition() != position) {
                        // 取消上个 item 的勾选状态
                        notifyItemChanged(position);
                        // 如果点击的不是当前选中的 item，则更新 selectedPosition 为新点击的 position
                        position = getAdapterPosition();
                        // 通知选中状态变化
                        notifyItemChanged(position);
                    }
                    // 触发点击事件的回调
                    onItemClickListener.onItemClick(itemView, position);
                }
            });
        }
    }
}