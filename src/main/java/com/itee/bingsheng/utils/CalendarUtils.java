package com.itee.bingsheng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils extends org.apache.commons.lang.time.DateUtils {

    public static final SimpleDateFormat DATE_WEEK = new SimpleDateFormat("EEEE");

    /**
     * 日期格式化
     */
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat _FORMAT_DATE =new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 年月格式化
     */
    public static final SimpleDateFormat FORMAT_YEAR_MONTH = new SimpleDateFormat("yyyy-MM");

    /**
     * 月日格式化
     */
    public static final SimpleDateFormat FORMAT_MONTH_DAY = new SimpleDateFormat("MM-dd");

    public static final SimpleDateFormat FORMAT_AAA_TIME_NOT_SECOND = new SimpleDateFormat("aaa HH:mm");

    /**
     * 时间格式化(不含秒)
     */
    public static final SimpleDateFormat FORMAT_TIME_NOT_SECOND = new SimpleDateFormat("HH:mm");

    /**
     * 时间格式化
     */
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");

    /**
     * 日期时间格式化(不含秒)
     */
    public static final SimpleDateFormat FORMAT_DATE_TIME_NOT_SECOND = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");

    /**
     * 日期格式化字符串
     */
    public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat _FORMAT_DATE_UNSIGNED = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 将时间格式化为日期时间格式(yyyy年MM月dd日)
     *
     * @param date
     * @return
     */
    public static String formatDateChina(Date date) {
        return formatDateByFormat(date, _FORMAT_DATE);
    }

    /**
     * 将时间格式化日期格式(yyyy-MM-dd)
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, FORMAT_DATE);
    }


    /**
     * 将时间格式化（yyyyMMddHHmmss）
     * @param date
     * @return
     */
    public static String formatDateToUnsigned(Date date) {
        return formatDateByFormat(date, _FORMAT_DATE_UNSIGNED);
    }

    public static String formatMonthDay(Date date) {
        return formatDateByFormat(date, FORMAT_MONTH_DAY);
    }

    /**
     * 将时间格式为时间形式(HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        return formatDateByFormat(date, FORMAT_TIME);
    }

    public static String formatDateToWeek(Date date) {
        return formatDateByFormat(date, DATE_WEEK);
    }

    /**
     * 将时间格式化为不带秒的时间形式(HH:mm)
     *
     * @param date
     * @return
     */
    public static String formatTimeNoSecond(Date date) {
        return formatDateByFormat(date, FORMAT_TIME_NOT_SECOND);
    }

    public static String formatAaaTimeNoSecond(Date date) {
        return formatDateByFormat(date, FORMAT_AAA_TIME_NOT_SECOND);
    }

    /**
     * 将时间格式化为日期时间格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDateByFormat(date, FORMAT_DATE_TIME);
    }


    /**
     * 将时间格式化为指定格式
     *
     * @param date
     * @return
     */
    public final static String formatDateByFormat(Date date, SimpleDateFormat format) {
        String result = "";
        if (date != null) {
            result = format.format(date);
        }
        return result;
    }

    /**
     * 将java.util.Date转化为java.sql.Date
     *
     * @param date
     * @return
     */
    public static java.sql.Date parseSqlDate(Date date) {
        if (date == null) {
            throw new NullPointerException();
        }
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期(一个月中的哪一天)
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取小时(24小时制)
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取毫秒
     *
     * @param date
     * @return
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 获取指定时间是星期几
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }


    /**
     * 日期相加
     *
     * @param date Date
     * @param day  int
     * @return Date
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减
     *
     * @param date  Date
     * @param date1 Date
     * @return int
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        return FORMAT_DATE.parse(date);
    }

    /**
     * 将字符串转换为日期时间格式的Date类型(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String date) throws ParseException {
        return FORMAT_DATE_TIME.parse(date);
    }
    /**
     * 将字符串转换为日期时间格式的Date类型(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDateTimeByYearMonth(String date) throws ParseException {
        return FORMAT_YEAR_MONTH.parse(date);
    }

    /**
     * 将字符串转换为日期时间格式的Date类型(yyyy-MM-dd HH:mm)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDateTimeNoSecond(String date) throws ParseException {
        return FORMAT_DATE_TIME_NOT_SECOND.parse(date);
    }

    public static boolean compareDateAndWeekDay(Date date, int weekDay) {
        if (weekDay == getWeek(date)) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串转换为日期时间格式的Date类型( MM-dd )
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseMonthDay(String date) throws ParseException {
        return FORMAT_MONTH_DAY.parse(date);
    }

    /**
     * 将字符串转为时间格式的Date类型(HH:mm)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseTimeNoSecond(String date) throws ParseException {
        return FORMAT_TIME_NOT_SECOND.parse(date);
    }

    /**
     * 将字符串转为时间格式的Date类型(HH:mm:ss)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseTimeNoDate(String date) throws ParseException {
        return FORMAT_TIME.parse(date);
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static String getCurrentDateTime() {
        Date date = new Date();
        String result = formatDateTime(date);
        return result;
    }

    /**
     * 时间比较yyyy-MM-dd
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDate(Date date1, Date date2) {
        String str1 = formatDate(date1);
        String str2 = formatDate(date2);
        if (str1.equals(str2)) {
            return true;
        }
        return false;
    }

    /**
     * 时间比较HH:mm
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDateTimeHHmm(Date date1, Date date2) {
        String str1 = formatTimeNoSecond(date1);
        String str2 = formatTimeNoSecond(date2);
        if (str1.equals(str2)) {
            return true;
        }
        return false;
    }

    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }



    public static Date getFormatDate(Date date) {
        Calendar cal = Calendar.getInstance();

        //设置当前时刻的时钟为0
        cal.set(Calendar.HOUR_OF_DAY, 0);
        //设置当前时刻的分钟为0
        cal.set(Calendar.MINUTE, 0);
        //设置当前时刻的秒钟为0
        cal.set(Calendar.SECOND, 0);
        //设置当前的毫秒钟为0
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        int year1 = cal1.get(Calendar.YEAR);
        int month1 = cal1.get(Calendar.MONTH);
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return true;
        }
        return false;
    }

    /**
     * 年份月份是否相同
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        int year1 = cal1.get(Calendar.YEAR);
        int month1 = cal1.get(Calendar.MONTH);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH);
        if (year1 == year2 && month1 == month2 ) {
            return true;
        }
        return false;
    }

    public static int daysBetween(Date date1, Date date2) {
        long qua = date1.getTime() - date2.getTime();
        return (int) (qua / (60 * 60 * 1000 * 24));
    }

    /** 获取两个时间的时间查 如1天2小时30分钟 */
    public static Long getDatePoor(Date endDate, Date nowDate) {
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        return diff / 1000 / 60; // 返回 分钟数
    }

    /**返回两个时间秒差
     * @param endDate:结束时间
     * @param nowDate：开始时间
     * @return
     */
    public static Long differenceSecond(Date endDate, Date nowDate) {
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        return diff / 1000 ; // 返回 秒数
    }

    /**
     * 根据日期参数以及 小时参数，获取 日期  n 0，则获取 date 参数值， 正数为相加 负数为想减
     * @return
     */
    public static Date getDateByHour(Date date,int n){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, n);
        return  calendar.getTime();
    }


    /**
     * 根据日期参数以及 分钟参数，获取 日期  n 0，则获取 date 参数值， 正数为相加 负数为想减
     * @return
     */
    public static Date getDateByMinute(Date date,int n){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, n);
        return  calendar.getTime();
    }

    /**
     * 获取今日开始时间和结束时间
     * @param date
     * @return
     */
    public static Date[] getDayTime(Date date){
        Date []  dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MINUTE, 00);
        dates[0] = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.SECOND, 59);
        calendar1.set(Calendar.MINUTE, 59);
        dates[1] = calendar1.getTime();
        return  dates;
    }

    /**
     * 获取某天的开始时间和结束时间
     * @param date
     * @return
     */
    public static Date[] getDayTime(Date date,int n){
        Date []  dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, n);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MINUTE, 00);
        dates[0] = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.DATE, n);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.SECOND, 59);
        calendar1.set(Calendar.MINUTE, 59);
        dates[1] = calendar1.getTime();
        return  dates;
    }

    /**
     * 获取上月开始时间和结束时间
     * @param date
     * @return
     */
    public static Date[] getMonthTime(Date date){
        Date []  dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MINUTE, 00);
        dates[0] = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.SECOND, 59);
        calendar1.set(Calendar.MINUTE, 59);
        dates[1] = calendar1.getTime();
        return  dates;
    }

    /**
     * 根据参数获取该月的开始时间和结束时间，n为推迟的周数，-1上月，0本月，1下月，依次类推
     * @param date
     * @return
     */
    public static Date[] getMonthTime(Date date,int n){
        Date []  dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MINUTE, 00);
        dates[0] = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.MONTH, n + 1);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.SECOND, 59);
        calendar1.set(Calendar.MINUTE, 59);
        dates[1] = calendar1.getTime();
        return  dates;
    }

    /**
     * 根据参数获取一周的开始时间和结束时间，n为推迟的周数，-1上一周，0本周，1下周，依次类推
     *
     * @param date
     * @return
     */
    public static Date[] getWeekDateTime(Date date,int n) {
        Date[] dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, n * 7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MINUTE, 00);
        dates[0] = calendar.getTime();

        calendar.set(Calendar.DAY_OF_WEEK, 7);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        dates[1] = calendar.getTime();
        return dates;
    }

    /**
     * 得到一天的最后一个时间点
     * @param date
     * @return
     */
    public static Date getDayDateOfLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        return calendar.getTime();
    }

    /**
     * 得到一天的第一个时间点
     * @param date
     * @return
     */
    public static Date getDayDateOfFirstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MINUTE, 00);
        return calendar.getTime();
    }

    /***
     * 得到上个个月的最初一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfPreMonth(Date date){
        Calendar   calendar   =Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return   calendar.getTime();
    }

    /***
     * 得到上个个月的最初一天
     * @param date
     * @return
     */
    public static Date getLastDayOfPreMonth(Date date){
        Calendar   calendar   =Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return   calendar.getTime();
    }

    /***
     * 计算自然月之间的月份差
     * @param begin
     * @param end
     * @return
     */
    public static int calculateMonthBetweenDate(Date begin,Date end){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(begin);
        c2.setTime(end);
        int year=c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        int  result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
        int amout=year*12+result;
        return amout;
    }


    public static void main(String[] args) throws ParseException {
        //  Date [] dates = getDayTime(new Date(),-1);
        // System.out.println(CalendarUtils.formatDateTime(dates[0]));
        //  System.out.println(CalendarUtils.formatDateTime(dates[1]));
        //System.out.print(CalendarUtils.formatDateTimeNotSecond(getDayDateOfFirstTime(new Date())) );

        Date startDate= CalendarUtils.parseDateTime("2017-05-03 16:31:28");
        Date endDate= CalendarUtils.parseDateTime("2017-05-03 16:31:59");
        System.out.println("时间差："+ CalendarUtils.differenceSecond(endDate,startDate));
    }
    public static Date localDateToDate(LocalDate localDate) {
        if(null != localDate){
            Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        }
        return null;
    }

}
