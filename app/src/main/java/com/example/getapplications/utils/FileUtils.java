package com.example.getapplications.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 将字符串写入到文本文件中
     * 该方法根据 append 参数的值决定是追加内容还是覆盖文件。
     * 如果 append 为 true，则在文件末尾追加内容。
     * 如果 append 为 false，则覆盖原有文件内容。
     *
     * @param content  要写入文件的字符串内容
     * @param filePath 文件所在的路径
     * @param fileName 要创建或写入的文件名
     * @param append   布尔值，指示是否追加内容
     */
    public static void writeToFile(String content, String filePath, String fileName, boolean append) {
        // 创建或确保文件路径存在
        File file = makeFile(filePath, fileName);

        // 写入内容，根据append参数决定追加或覆盖
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            writer.write(content);
        } catch (IOException e) {
            Log.e(TAG, "写入错误: " + e.getMessage());
        }
    }

    /**
     * 创建一个新的文件或检查文件是否存在
     * 该方法会检查指定的文件是否已经存在，如果不存在则创建一个新文件。
     *
     * @param filePath 指定文件所在的路径
     * @param fileName 要创建的文件的名称，应包含扩展名，如 "example.txt"
     * @return 返回创建或已存在的文件的File对象
     */
    public static File makeFile(String filePath, String fileName) {
        // 确保文件路径存在
        makeDirectory(filePath);

        // 创建一个新的File对象并检查是否存在，如果不存在则创建
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    Log.e(TAG, "无法创建文件: " + fileName);
                }
            } catch (IOException e) {
                Log.e(TAG, "生成文件错误: " + e.getMessage());
            }
        }
        return file;
    }

    /**
     * 创建文件夹
     * 如果文件夹已经存在，则不会重复创建。
     *
     * @param filePath 要创建的文件夹的路径
     */
    public static void makeDirectory(String filePath) {
        // 创建一个File对象，用于表示指定的文件夹路径
        File directory = new File(filePath);

        // 检查文件夹是否存在
        if (!directory.exists()) {
            // 尝试创建目录，如果失败，将打印错误日志
            if (!directory.mkdirs()) {
                Log.e(TAG, "无法创建文件夹: " + filePath);
            }
        }
        // 如果文件夹已存在，则不需要执行任何操作
    }
}
