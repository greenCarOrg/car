package com.itee.bingsheng.utils;

import javax.validation.constraints.NotNull;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gonglei 统一采用高位在前，低位在后
 */
public class NumberCoder {

    public static byte[] double2bytes(double d) {
        long num = Double.doubleToLongBits(d);
        return long2bytes(num);
    }

    public static byte[] float2Bytes(float f) {
        int num = Float.floatToIntBits(f);
        return int2bytes(num);
    }

    public static byte[] long2bytes(long num) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) (num >>> (56 - i * 8));
        }
        return b;
    }

    public static byte[] int2bytes(int num) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (num >>> (24 - i * 8));
        }
        return b;
    }

    public static int byteToInt(byte[] byteVal) {
        int result = 0;
        for (int i = 0; i < byteVal.length; i++) {
            int tmpVal = ((byteVal[i] & 0xFF) << (8 * (3 - i)));
            switch (i) {
                case 0:
                    tmpVal = tmpVal & 0xFF000000;
                    break;
                case 1:
                    tmpVal = tmpVal & 0x00FF0000;
                    break;
                case 2:
                    tmpVal = tmpVal & 0x0000FF00;
                    break;
                case 3:
                    tmpVal = tmpVal & 0x000000FF;
                    break;
            }
            result = result | tmpVal;
        }
        return result;
    }

    /**
     * 高位在前，低位在后
     *
     * @param bytes
     * @return
     */
    public static int shortByteToInt(byte[] bytes) {
        int result = 0;
        int tmpVal1 = ((bytes[0] & 0xFF) << 8);
        tmpVal1 = tmpVal1 & 0xFF00;
        result = result | tmpVal1;

        int tmpVal = (bytes[1] & 0xFF);
        tmpVal = tmpVal & 0x00FF;
        result = result | tmpVal;
        return result;
    }

    public static float getFloat(byte[] b) {
        return Float.intBitsToFloat(byteToInt(b));
    }

    /**
     * @功能: 将一个short值转为byte数组
     * @参数: short sNum 要转的short值
     * @返回值: byte[] bytesRet 转后的byte数组
     */
    public static byte[] shortToBytes(short sNum) {
        byte[] bytesRet = new byte[2];
        bytesRet[0] = (byte) ((sNum >> 8) & 0xFF);
        bytesRet[1] = (byte) (sNum & 0xFF);
        return bytesRet;
    }

    /**
     * 求给定的byte值的二进制位的第n位是否为1
     *
     * @param i
     * @param n 从0开始
     * @return
     * @author moxin
     */
    public static boolean specialBitIsOne(byte i, int n) {
        return (i & pow2(n)) / pow2(n) == 1;
    }

    /**
     * 设置给定的byte值的第n位为1
     *
     * @param i
     * @param n
     * @return
     */
    public static byte specialBitToOne(byte i, int n) {
        return (byte) (i | (0x1 << n));
    }

    /**
     * 设置给定的byte值的第n位的值
     *
     * @param i
     * @param n
     * @return
     */
    public static byte specialBitToOne(byte i, int n, boolean flag) {
        int value = (flag ? 1 : 0);
        return (byte) (i | (value << n));

    }

    /**
     * 设置给定的byte数组的第n个bit位的值为1
     *
     * @param bytes
     * @param n
     * @param start
     * @return
     */
    public static byte[] specialByteToOne(byte[] bytes, int n, int start) {
        int i = n / 8 + start;
        specialBitToOne(bytes[i], n);
        return bytes;

    }

    /**
     * 求给定的byte值的二进制位的第start到end时候存在1
     *
     * @param i
     * @param start
     * @param end
     * @return
     */
    public static boolean specialBitIsExistOne(byte i, int start, int end) {
        for (int j = start; j <= end; j++) {
            if (specialBitIsOne(i, j)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 求2的平方(int值)
     *
     * @param n
     * @return
     * @author moxin
     */
    public static int pow2(int n) {
        return 1 << n;
    }

    /**
     * 检验字符串是否是由数字组成
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 获取一个给定byte值的补码
     *
     * @param i
     * @return
     */
    public static byte[] complement(byte i) {
        byte[] b = new byte[8];
        for (int n = 0; n <= 7; n++) {
            b[7 - n] = (byte) ((i & pow2(n)) / pow2(n));
        }
        return b;
    }

    /**
     * 将16进制书转成10进制的小数
     *
     * @param bytes
     * @param decimals
     * @return
     */
    public static float hexByte2Float(byte[] bytes, int decimals) {
        float f = 0;
        for (int i = 0; i < bytes.length; i++) {
            int data = bytes[i] & 0xFF;
            if (data > 0xA0) {
                return 0;
            }
            int b = data - (data / 16) * 6;
            f += Math.pow(100, i - decimals) * b;
        }
        return f;
    }

    /**
     * 将年月日转成byte
     *
     * @param date
     * @return
     */
    public static byte[] dateToByte(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        byte[] bytes = new byte[3];
        bytes[0] = (byte) (calendar.get(Calendar.YEAR) - 2000);
        bytes[1] = (byte) calendar.get(Calendar.MONTH);
        bytes[2] = (byte) calendar.get(Calendar.DAY_OF_MONTH);
        return bytes;
    }

    /**
     * 将byte转换成date
     *
     * @param bytes
     * @return
     */
    public static Date byteToDate(byte[] bytes) {
        int length = bytes.length;
        Calendar calendar = Calendar.getInstance();
        switch (length) {
            // yyyy-MM-dd hh-mm
            case 5:
                calendar.set(Calendar.YEAR, bytes[0] + 2000);
                calendar.set(Calendar.MONTH, bytes[1]);
                calendar.set(Calendar.DAY_OF_MONTH, bytes[2]);
                calendar.set(Calendar.HOUR_OF_DAY, bytes[3]);
                calendar.set(Calendar.MINUTE, bytes[4]);
                break;
            // yyyy-MM-dd
            case 3:
                calendar.set(Calendar.YEAR, bytes[0] + 2000);
                calendar.set(Calendar.MONTH, bytes[1]);
                calendar.set(Calendar.DAY_OF_MONTH, bytes[2]);
                break;
            // hh-mm
            case 2:
                calendar.set(Calendar.HOUR_OF_DAY, bytes[0]);
                calendar.set(Calendar.MINUTE, bytes[1]);
                break;
        }
        return calendar.getTime();
    }

    /**
     * 将年月日时分转成byte
     *
     * @param date
     * @return
     */
    public static byte[] dateTimeToByte(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        byte[] bytes = new byte[5];
        bytes[0] = (byte) (calendar.get(Calendar.YEAR) - 2000);
        bytes[1] = (byte) calendar.get(Calendar.MONTH);
        bytes[2] = (byte) calendar.get(Calendar.DAY_OF_MONTH);
        bytes[3] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        bytes[4] = (byte) calendar.get(Calendar.MINUTE);
        return bytes;
    }

    /**
     * 将时分转成byte
     *
     * @param date
     * @return
     */
    public static byte[] timeToByte(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        byte[] bytes = new byte[2];
        bytes[0] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        bytes[1] = (byte) calendar.get(Calendar.MINUTE);
        return bytes;
    }

    /**
     * 给数组设置初始值
     *
     * @param bytes
     * @param start
     * @param length
     * @param value
     * @return
     */
    public static byte[] initialValue(byte[] bytes, int start, int length, byte value) {
        for (int i = start; i < start + length; i++) {
            bytes[i] = value;
        }
        return bytes;
    }

    /**
     * 格式化为两位小数，并带单位的字符串
     *
     * @param num
     * @param unit
     * @return
     */
    public static String formatNumber(double num, String unit) {
        return formatNumber(num, 2, unit);
    }

    /**
     * 格式化为指定小数位的小数，并带单位的字符串
     *
     * @param num
     * @param unit
     * @return
     */
    public static String formatNumber(double num, int radix, String unit) {
        if (radix <= 0) {
            throw new RuntimeException("小数位数不能小于0");
        }
        String radixs = "";
        for (int i = 0; i < radix; i++) {
            radixs += "#";
        }
        DecimalFormat format = new DecimalFormat("0." + radixs);
        return format.format(num) + (unit == null ? "" : unit);
    }

    /**
     * 格式化亮灯时长的显示格式
     *
     * @param lightTime
     * @return
     */
    public static String formatLightTime(int lightTime) {
        double hours = lightTime / 3600.0;
        return formatNumber(hours, null);
    }

    /**
     * 将data字节型数据转换为0~255 (0xFF 即BYTE)。
     *
     * @param data
     * @return
     */
    public static int getUnsignedByte(byte data) {
        return data & 0x0FF;
    }

    /**
     * 将data字节型数据转换为0~65535 (0xFFFF 即WORD)
     *
     * @param data
     * @return
     */
    public static int getUnsignedShort(short data) {
        return data & 0x0FFFF;
    }

    /**
     * 将int数据转换为0~4294967295(0xFFFFFFFF即DWORD)。
     *
     * @param data
     * @return
     */
    public static long getUnsignedInt(int data) {
        return data & 0x0FFFFFFFFl;
    }

    /**
     * 计算燕赵仪表modbus数据 共32位， 1符号位，8指数位，23尾数位... IEE754 协议
     *
     * @param bytes
     * @return
     */
    public static double modBusData(byte[] bytes) {
        double data = 0;
        int idata = byteToInt(bytes);
        int e = ((idata >> 23) & 0xFF) - 126;
        if (e < 4) {
            return 0;
        }
        long tail = (idata & 0x00ffffff) | 0x00800000;
        data = (Math.pow(2, e) * tail) / (256 * 65536);
        return data;

    }

    public static byte[] phoneNumber2bytes(String phoneNumber) {
        byte[] bytes = new byte[11];
        if (phoneNumber.length() == 11) {
            for (int i = 0; i < phoneNumber.length(); i++) {
                bytes[i] = phoneNumber.substring(i, i + 1).getBytes()[0];
            }
            return bytes;
        }
        return bytes;
    }

    public static int byteToInt(@NotNull byte[] src, @NotNull ByteOrder endian) {
        assert src.length > 0 && src.length <= 4;
        assert endian == ByteOrder.BIG_ENDIAN || endian == ByteOrder.LITTLE_ENDIAN;

        int result = 0;
        if (endian == ByteOrder.BIG_ENDIAN) {
            for (int i = 0; i < src.length; i++) {
                result |= getUnsignedByte(src[i]) << (i * 8);
            }
        } else {
            for (int i = 0, j = src.length - 1; i < src.length; i++, j--) {
                result |= getUnsignedByte(src[i]) << (j * 8);
            }
        }
        return result;
    }
}
