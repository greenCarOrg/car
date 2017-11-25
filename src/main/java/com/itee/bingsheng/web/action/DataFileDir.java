package com.itee.bingsheng.web.action;

import javax.servlet.ServletContext;
import java.io.File;

public class DataFileDir {

    public static final String DATA_DIR = "/data";



    /**
     * 用户图像目录
     */
    public static String getUserFaceDir(int userId) {
        String path = DATA_DIR + File.separator + "user" + File.separator + userId;
        return path;
    }

    public static String getStudentFaceDir(int studentId) {
        String path = DATA_DIR + File.separator + "student" + File.separator + studentId;
        return path;
    }

    public static String getChargeImageDir() {
        String path = DATA_DIR + File.separator + "chargeImage";
        return path;
    }
    public static String getFeedBackChargeImageDir() {
        String path = DATA_DIR + File.separator + "FeedBackImage";
        return path;
    }


    /**
     * 得到服务器绝对文件路径
     */
    public static String getServletContextPath(ServletContext servletContext, String path) {
        String contextPath = servletContext.getRealPath(path); // 获取本地存储路径
        File fileDir = new File(contextPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return contextPath;
    }

    public static File getServletContextFileDir(ServletContext servletContext, String path) {
        String contextPath = servletContext.getRealPath(path); // 获取本地存储路径
        File fileDir = new File(contextPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

}
