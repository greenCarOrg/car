package com.itee.bingsheng.utils;


import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class GeoUtil {


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 地球半径：6378.137KM
     */
    private static double EARTH_RADIUS = 6378.137;

    /**
     * 调用百度接口的应用key值
     *
     */
    private final static String BAIDU_KEY = "1vQskKt1u9q4YTD3xMLcofl2";


    /**
     * 根据经纬度和距离返回一个矩形范围
     *
     * @param lng      经度
     * @param lat      纬度
     * @param distance 距离(单位为米)
     * @return [lng1, lat1, lng2,lat2] 矩形的左下角(lng1,lat1)和右上角(lng2,lat2)
     */
    public static double[] getRectangle(double lng, double lat, long distance) {
        float delta = 111000;
        if (lng != 0 && lat != 0) {
            double lng1 = lng - distance / Math.abs(Math.cos(Math.toRadians(lat)) * delta);
            double lng2 = lng + distance / Math.abs(Math.cos(Math.toRadians(lat)) * delta);
            double lat1 = lat - (distance / delta);
            double lat2 = lat + (distance / delta);
            return new double[]{lng1, lat1, lng2, lat2};
        } else {
            // TODO ZHCH 等于0时的计算公式
            double lng1 = lng - distance / delta;
            double lng2 = lng + distance / delta;
            double lat1 = lat - (distance / delta);
            double lat2 = lat + (distance / delta);
            return new double[]{lng1, lat1, lng2, lat2};
        }
    }

    /**
     * 得到两点间的距离 米
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistanceOfMeter(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10;
        return s;
    }


    /**
     * 根据地理位置,调用百度API,获得经纬度
     *
     * @param cityName
     *            省份+城市: 如:黑龙江哈尔滨市(注意前后/中间不能含有空格)
     * @param address
     *            详细地址: 如: 南岗区闽江路103号(注意前后/中间不能含有空格)
     * @return 含有经度和纬度的map
     */
    public static Map<String, Float> getBaiduLngLat(String cityName, String address) {
        Map<String, Float> map = new HashMap<String, Float>();
        Float startLng = 0.0f;
        Float startLat = 0.0f;

        StringBuffer sb = new StringBuffer();
        sb.append("output=json");
        sb.append("&ak=" + BAIDU_KEY);
        // 省份+城市
        sb.append("&cityName=" + cityName);
        // 省份+城市+详细地址
        sb.append("&keyWord=" + cityName + address);
        sb.append("&out_coord_type=bd09ll");

        // 得到百度返回的json串
        // 返回格式:{"status":"Success","results":{"precise":1,"location":{"lng":126.70395957002,"lat":45.746959975657}}}
        String result = sendGet("http://api.map.baidu.com/telematics/v3/geocoding", sb.toString());

        // 得到key status的值,并判断是否等于Success
        String success = getJsonKey(result, "status");
        if ("Success".equals(success)) {

            // 下面的代码可以考虑用递归解析出来.暂未封装...
            String results = getJsonKey(result, "results");
            String location = getJsonKey(results, "location");
            String lng = getJsonKey(location, "lng"); // 得到经度
            String lat = getJsonKey(location, "lat"); // 得到纬度
            startLng = Float.parseFloat(lng);
            startLat = Float.parseFloat(lat);
        }
        map.put("lng", startLng);
        map.put("lat", startLat);
        return map;
    }

    /**
     * 根据经纬度, 查询地理位置
     *
     * @param location
     *            经度和纬度组成, 中间用逗号隔开 http://api.map.baidu.com/telematics/v3/reverseGeocoding?output=json&ak=vOwsIGlUwmufgbX9p3Czlnbd&location=126.710495,45.753723
     */
    public static String getBaiduAddress(String location) {
        String address = "";

        StringBuffer sb = new StringBuffer();
        sb.append("output=json");
        sb.append("&ak=" + BAIDU_KEY);
        sb.append("&location=" + location);
        sb.append("&coord_type=bd09ll");

        // 得到百度返回的json串
        String result = sendGet("http://api.map.baidu.com/telematics/v3/reverseGeocoding", sb.toString());
        // 得到key status的值,并判断是否等于Success
        String success = getJsonKey(result, "status");
        if ("Success".equals(success)) {
            address = getJsonKey(result, "description");
        }
        return address;
    }

    /**
     * 调用百度API,获得两个点之间的距离
     * @param lng1Str
     * @param lat1Str
     * @param lng2Str
     * @param lat2Str
     * @return
     */
    public static Double distance(String lng1Str, String lat1Str, String lng2Str, String lat2Str) {
        StringBuffer sb = new StringBuffer();
        sb.append("output=json");
        sb.append("&ak=" + BAIDU_KEY);
        sb.append("&waypoints=" + lng1Str + "," + lng1Str + "," + lng2Str + "," + lat2Str);

        // 得到百度返回的json串
        String result = sendGet("http://api.map.baidu.com/telematics/v3/distance", sb.toString());
        System.out.println(result);
        return null;
    }

    /**
     *  BD-0911百度坐标转换成 GCJ-02国测局加密的坐标(或者高德坐标)
     * @param bd_lat 纬度
     * @param bd_lon 经度
     * @return
     */
    public static Map<String, Float> toGaoDeLngLat(double bd_lat, double bd_lon) {
        Map<String, Float> map = new HashMap<String, Float>();
        double gg_lat;
        double gg_lon;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
        gg_lon = z * Math.cos(theta);
        gg_lat = z * Math.sin(theta);
        map.put("lat", Float.parseFloat(String.valueOf(gg_lat)));
        map.put("lng", Float.parseFloat(String.valueOf(gg_lon)));
        return map;
    }


    /**
     * 得到json串里面里面的key值
     *
     * @param json
     * @param key
     * @return
     */
    public static String getJsonKey(String json, String key) {
        String result = "";

        JSONObject jsonObj = JSONObject.fromObject(json);
        if (jsonObj.containsKey(key)) {
            result = jsonObj.get(key).toString();
        }
        return result;
    }

    /**
     * 获取指定IP对应的经纬度（为空返回当前机器经纬度）
     *
     * @param ip
     * @return
     */
    public static String[] getIPXY(String ip)throws Exception {
        try {
            URL url = new URL("https://api.map.baidu.com/location/ip?ak="+BAIDU_KEY+"&ip=" + ip + "&coor=bd09ll");
            InputStream inputStream = url.openStream();
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputReader);
            StringBuffer sb = new StringBuffer();
            String str;
            do {
                str = reader.readLine();
                sb.append(str);
            } while (null != str);

            str = sb.toString();
            if (null == str || str.isEmpty()) {
                return null;
            }

            int index = str.indexOf("point");
            int end = str.indexOf("}}", index);

            if (index == -1 || end == -1) {
                return null;
            }

            str = str.substring(index - 1, end + 1);
            if (null == str || str.isEmpty()) {
                return null;
            }

            String[] ss = str.split(":");
            if (ss.length != 4) {
                return null;
            }
            String x = ss[2].split(",")[0];
            String y = ss[3];

            x = x.substring(x.indexOf("\"") + 1, x.indexOf("\"", 1));
            y = y.substring(y.indexOf("\"") + 1, y.indexOf("\"", 1));

            return new String[] { x, y };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }




}
