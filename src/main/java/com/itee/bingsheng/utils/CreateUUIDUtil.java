package com.itee.bingsheng.utils;

import java.util.UUID;
import java.util.regex.Pattern;


public class CreateUUIDUtil {
    public static String createUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

}
