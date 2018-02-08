package com.thw.file;

import android.os.Environment;

import java.io.File;

public class AppConfig {
    /**
     * describe app资源根目录
     */
    public static String APP_SOURCE_DIR = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "OperateFileToJavaWeb/";

    /**
     * DATABASE_NAME : 数据库名称
     * DATABASE_PATH ：数据库保存路径
     * DATABASE_VERSION ： 数据库版本
     */
    public static String DATABASE_NAME = "yuxuanDance.db";
    public static String DATABASE_PATH = APP_SOURCE_DIR + "database/";
    public static int DATABASE_VERSION = 2;

    /**
     * describe app音乐下载地址
     */
    public static String APP_SAVE_MUSIC_PATH = APP_SOURCE_DIR + "music/";

    /**
     * app视频下载地址
     */
    public static String APP_SAVE_VIDEO_PATH = APP_SOURCE_DIR + "video/";

    /**
     * app图片下载地址
     */
    public static String APP_SAVE_PHOTO_PATH = APP_SOURCE_DIR + "photo/";

    /**
     * app版本更新包地址
     */
    public static String APP_SAVE_UPDATE_PATH = APP_SOURCE_DIR + "update/";

    /**
     * 设置根目录
     *
     * @param dir
     */
    public static void setDir(String dir) {
        APP_SOURCE_DIR = dir;
    }

    /**
     * 设置音乐目录
     *
     * @param dir
     */
    public static void setMusicPath(String dir) {
        APP_SAVE_MUSIC_PATH = dir;
    }

    /**
     * 设置视频目录
     *
     * @param dir
     */
    public static void setVideoPath(String dir) {
        APP_SAVE_VIDEO_PATH = dir;
    }

    /**
     * 设置图片目录
     *
     * @param dir
     */
    public static void setAppSavePhotoPath(String dir) {
        APP_SAVE_PHOTO_PATH = dir;
    }

    /**
     * 设置安装包目录
     *
     * @param dir
     */
    public static void setAppSaveUpdatePath(String dir) {
        APP_SAVE_UPDATE_PATH = dir;
    }
}
