package com.itee.bingsheng.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContextException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;


public class TKFile {

    public static boolean createDir(String sDir) {
        if (sDir == null || "".equals(sDir.trim()))
            return false;

        try {
            File fDirFile = new File(sDir);

            if (!fDirFile.exists()) {
                if (!new File(fDirFile.getParent()).exists()) {
                    createDir(fDirFile.getParent());
                }
                fDirFile.mkdir();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean isFileExist(String sFileFullPath) {
        if (sFileFullPath == null || "".equals(sFileFullPath.trim()))
            return false;
        try {
            //�����ļ�Ŀ¼
            File dir = new File(sFileFullPath);
            return dir.exists();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean isFileExist(String sFilePath, String sFileName) {
        if (sFilePath == null || "".equals(sFilePath.trim()) || sFileName == null || "".equals(sFileName.trim()))
            return false;
        try {
            //�����ļ�Ŀ¼
            File dir = new File(sFilePath + "\\" + sFileName);
            return dir.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean deleteFile(String sFileFullPath) {
        if (sFileFullPath == null || "".equals(sFileFullPath.trim()))
            return false;
        try {
            //�����ļ�Ŀ¼
            File file = new File(sFileFullPath);
            if (!file.exists()) {
                return true;
            }

            file.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean deleteFile(String sFilePath, String sFileName) {
        if (sFilePath == null || "".equals(sFilePath.trim()) || sFileName == null || "".equals(sFileName.trim()))
            return false;
        try {
            File file = new File(sFilePath + "\\" + sFileName);
            if (!file.exists()) {
                return true;
            }

            file.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static byte[] getBytesFromFile(File file) {
        if (file == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }

            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getPropValue(String sPropFileName, String sParamName) {
        if (sPropFileName == null || "".equals(sPropFileName.trim()) || sParamName == null || "".equals(sParamName.trim()))
            return null;
        PropertiesConfiguration config = getPropertiesConfiguration(sPropFileName);
        if (config == null)
            return null;
        String sValue = config.getString(sParamName);
        if (sValue == null || "".equals(sValue.trim()))
            return null;
        return sValue;
    }


    public static PropertiesConfiguration getPropertiesConfiguration(String sPropName) {
        PropertiesConfiguration config;
        try {
            File file = new File(TKFile.class.getClassLoader().getResource(sPropName).toURI());
            config = new PropertiesConfiguration(file);
//            System.out.printf(config.getString("jdbc.url"));
//            config = new PropertiesConfiguration(sPropName);
        } catch (ConfigurationException e) {
            throw new ApplicationContextException("Can not load PropertiesConfiguration!", e);
        } catch (URISyntaxException e) {
            return null;
        }
        return config;
    }

    public static void main(String[] args) {
        getPropertiesConfiguration("application.properties");
    }



    public static File getTemporaryDir() {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "bingsheng");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        return tempDir;
    }

}
