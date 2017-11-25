package com.itee.bingsheng.utils;

import java.util.Date;
import java.util.Random;

public class RandomUtil {

    /**
     * 生成随机数字和字母
     * @param length 位数长度
     * @return 随机数
     * 2015年6月25日 下午3:54:36
     */
    public static String getStringRandom(int length) {
         
        String val = "";
        Random random = new Random();
         
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
             
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
	/**
	 * 产生6位随机数(数字)
	 * @return
	 * @Time 2015-2-8 下午03:01:49
	 */
	public static String getSixNumber() {
		String str  = (Math.random()*9000+100000) + "";
		str= str.substring(0, 6);
		return str;
	}

    /**
     * 获得任意长度随机数
     * @param length
     * @return
     */
    public static String getRadom(int length) {
        //获取一个随机数
        double rand = Math.random();
        //将随机数转换为字符串
        String str = String.valueOf(rand).replace("0.", "");
        //截取字符串
        String newStr = str.substring(0, length);
        return newStr;
    }

	/**生成时间类型的订单no
	 * @return
	 */
	public static String getOrderNo() {
		return CalendarUtils.formatDateToUnsigned(new Date())+getRadom(4);
	}
}
