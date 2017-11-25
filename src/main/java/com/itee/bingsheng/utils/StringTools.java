package com.itee.bingsheng.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringTools {

    public static  String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getLocalIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    //	private static MessageDigest message = null;
    private static char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static SimpleDateFormat secondFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat secondFormat_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
    private static DecimalFormat numFormat_2 = new DecimalFormat("#.00");
    private static DecimalFormat numFormat_4 = new DecimalFormat("0000");

    public static boolean isNum(String str) {
        if (str == null || "".equals(str.trim())) return false;
        Pattern p = Pattern.compile("[0-9]*(\\.[0-9]*)?");
        Matcher m = p.matcher(str.trim());
        return m.matches();
    }

    public static boolean isPhoneNum(String str) {
        Matcher m = Pattern.compile("(13|14|15|18)[0-9]{9}").matcher(str);
        return m.matches();
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String getMD5(String src) {
        MessageDigest message = null;
        try {
            message = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        message.update(src.getBytes());
        byte[] b = new byte[16];
        b = message.digest();
        String digestHexStr = "";
        for (int i = 0; i < 16; i++) {
            digestHexStr += byteHEX(b[i]);
        }
        return digestHexStr;
    }

    public static String getSHA256(String src) {
        MessageDigest message = null;
        try {
            message = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        message.update(src.getBytes());
        byte[] b = new byte[16];
        b = message.digest();
        String digestHexStr = "";
        for (int i = 0; i < 16; i++) {
            digestHexStr += byteHEX(b[i]);
        }
        return digestHexStr;
    }

    public static String byteHEX(byte ib) {
        char[] ob = new char[2];
        ob[0] = digit[(ib >>> 4) & 0X0F];
        ob[1] = digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static String getMessageDigest(byte[] buffer, String key) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(key);
            digest.update(buffer);
            return bytes2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static Integer toNum(String str, Integer def) {
        try {
            if (str == null || str.isEmpty()) return def;
            return Integer.valueOf(str);
        } catch (Exception e) {
            return def;
        }
    }


    public static String getStr(Object object, String defVal) {
        String string = (String) object;
        if (Strings.isNullOrEmpty(string))
            return defVal;
        return string;
    }

    public static double strToDouble(String str) {
        try {
            if (!Strings.isNullOrEmpty(str)) {
                return Double.parseDouble(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.00;
    }


    public static String ebToRmb(String ebString) {
        try {
            if (!Strings.isNullOrEmpty(ebString)) {
                return Math.floor(Double.parseDouble(ebString) / 100) + "";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static String getSpecVlaue(String src, String spec1, String spec2) {
        int offset1 = src.indexOf(spec1);
        if (spec1.equals("")) offset1 = 0;
        if (offset1 == -1) return "";
        int offset2 = src.indexOf(spec2, offset1 + spec1.length());
        if (spec2.equals("")) offset2 = src.length();
        if (offset2 == -1 || offset1 > offset2) {
            return "";
        } else {
            return src.substring(offset1 + spec1.length(), offset2);
        }

    }

//    public static void main2(String[] args) {
//        String string = "<notify><trade_status>TRADE_FINISHED</trade_status><total_fee>0.90</total_fee><subject>123456</subject><out_trade_no>1118060201-7555</out_trade_no><notify_reg_time>2010-11-1814:02:43.000</notify_reg_time><trade_no>2010111800209965</trade_no></notify>";
//        parserSimpleXmlStr(string);
//    }


    /**
     * 只能解析层的xml结构字符<Root><E1>V1</E1><E2>V2</E2></Root> , key名称全部为小写；
     *
     * @param xmlString
     * @return
     */
    public static HashMap<String, String> parserSimpleXmlStr(String xmlString) {
        if (xmlString == null || xmlString.isEmpty())
            return null;
        SAXReader saxReader = new SAXReader();
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
            Document document = saxReader.read(stream);
            Element root = document.getRootElement();
            for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                map.put(element.getName(), element.getTextTrim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static SimpleDateFormat getSecondFormat() {
        return secondFormat;
    }

    public static void setSecondFormat(SimpleDateFormat secondFormat) {
        StringTools.secondFormat = secondFormat;
    }

    public static SimpleDateFormat getSecondFormat_() {
        return secondFormat_;
    }

    public static void setSecondFormat_(SimpleDateFormat secondFormat_) {
        StringTools.secondFormat_ = secondFormat_;
    }


    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static String DisEncode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static int strToInt(String str, int defVal) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static DecimalFormat getNumFormat_2() {
        return numFormat_2;
    }

    public static void setNumFormat_2(DecimalFormat numFormat_2) {
        StringTools.numFormat_2 = numFormat_2;
    }

    public static SimpleDateFormat getHourFormat() {
        return hourFormat;
    }

    public static void setHourFormat(SimpleDateFormat hourFormat) {
        StringTools.hourFormat = hourFormat;
    }


    /**
     * 获取个编码过的URL
     */
    public static synchronized String getEncodedUrl(TreeMap<String, String> map) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entity : map.entrySet()) {
            sb.append(entity.getKey()).append("=").append(URLEncoder.encode(entity.getValue(), "UTF-8")).append("&");
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static DecimalFormat getNumFormat_4() {
        return numFormat_4;
    }

    public static void setNumFormat_4(DecimalFormat numFormat_4) {
        StringTools.numFormat_4 = numFormat_4;
    }


    /**
     * 正则验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        if (StringUtils.isBlank(email)) return false;

        String regular = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern pattern = Pattern.compile(regular, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**去除字符串中的空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**产生随机字符串
     * @param length：表示生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    public static void main(String args[]) {
//        String str = getMD5("9B740CC84570A1E9E411781B9B87B942" + "1413023411310" + "15999603218" + "&" + "@Ihuizhi.com.ASBSD");
//        System.out.println(str);
//        System.out.println(replaceBlank("c5uB5JSd17McZ9VWBiqh9W+5OYrdgyrRQpvm7H44wO7SlmjHUYUBh92GEoF6R8FFRCC+J3ml9M4R\n" +
//                "M2itCXWFp0g0kZ/oCNSsY7++fbaJ3XCt58+gF3WvazpByMULxSR/huFgPHWUr+uvbr8Slgszi47e\n" +
//                "ulRUdcZ48mXxzVCVEzw="));
        System.out.println(getRandomString(32));
    }

}
