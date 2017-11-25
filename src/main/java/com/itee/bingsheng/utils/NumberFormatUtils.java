package com.itee.bingsheng.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class NumberFormatUtils {
    public static final DecimalFormat FORMAT_TWOPOINT = new DecimalFormat("###,###,##0.00");

    /**
     * 数据格式化
     *
     * @param value
     * @param scale 保留几位小数
     * @return
     */
    public static Double round(Double value, int scale) {
        double result = 0.0;
        if (null != value) {
            result = new BigDecimal(String.valueOf(value)).setScale(scale,
                    BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return result;
    }
    /**
     * 数据格式化
     * @param value
     * @param scale 保留几位小数
     * @return
     */
    public static Float round(Float value, int scale) {
        float result = 0;
        if (null != value) {
            result = new BigDecimal(String.valueOf(value)).setScale(scale,
                    BigDecimal.ROUND_HALF_UP).floatValue();
        }
        return result;
    }

    public static String round(Double value){
        if(null != value){
            return FORMAT_TWOPOINT.format(value);
        }
        return "";
    }
}
