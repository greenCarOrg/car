package com.itee.bingsheng.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyUtil {


    private static Properties properties;

    static  {
        properties =  new  Properties();
        InputStream in = Object. class .getResourceAsStream( "/resource/application.properties" );
        try  {
            properties.load(in);
        }  catch  (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ˽�й��췽��������Ҫ��������
     */
    private PropertyUtil() {

    }

    /**
     * ��ȡ�����ļ�����
     * @return
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * ������������ȡObject��������ֵ
     * @param key
     * @return
     */
    public static  Object get(String key){
        if(properties != null){
            return properties.get(key);
        }
        return null;
    }

    /**
     * ������������ȡ�ַ�����������ֵ
     * @param key
     * @return
     */
    public static String getProperty(String key){
        if(properties != null){
            return properties.getProperty(key).trim();
        }
        return null;
    }

    public static void main(String [] args){
        System.out.println(getProperty("serverUrl"));
    }
}
