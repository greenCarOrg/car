package com.itee.bingsheng.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Calendar;

/* *
 *类名：DateUtils
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 */
public class DateUtils {
	
    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String dtLong                  = "yyyyMMddHHmmss";
    
    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String simple                  = "yyyy-MM-dd HH:mm:ss";
    
    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtShort                 = "yyyyMMdd";
    
    /** 年月日(无下划线) YYMMDDHHMMSS */
    public static final String refund				   = "yyMMddHHmmss";

	/** 年月日时分秒(无下划线) yyyyMMddHHmmssSSS */
	public static final String dtStr					="yyMMddHHmmssSSS";

	public static final String datePatten				= "yyyy-MM-dd";

    /**
     * 获取YYMMDDHHMMSS格式时间
     * @return
     */
    public static String getDateForm(){
    	Date date = new Date();
    	DateFormat df = new SimpleDateFormat(refund);
    	return df.format(date);
    }
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyyyMMddHHmmss为格式的当前系统时间
     */
	public  static String getOrderNum(){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtStr);
		Random rand = new Random();
		int i = rand.nextInt(); //int范围类的随机数
		i = rand.nextInt(9); //生成0-100以内的随机数
		return df.format(date)+i;
	}
	
	public  static String getDateTime(Date date){
		DateFormat df=new SimpleDateFormat(dtLong);
		return df.format(date);
	}


	public  static String getDateTime(Date date,String dt){
		DateFormat df=new SimpleDateFormat(dt);
		return df.format(date);
	}
	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	
	/**
	 * 产生随机的三位数
	 * @return
	 */
	public static String getThree(){
		Random rad=new Random();
		return rad.nextInt(1000)+"";
	}
	/**
	 * 根据两个时间获取时差
	 * @param rDate
	 * @return
	 * @throws ParseException
	 */
	public static Integer getDiff(String rDate,String nows) throws ParseException{
		StringBuilder sb = new StringBuilder();
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now = df.parse(nows);
	    Date date = df.parse(rDate);
	    long l=now.getTime()-date.getTime();
	    long day=l/(24*60*60*1000);
	    long hour=(l/(60*60*1000)-day*24);
	    long min=((l/(60*1000))-day*24*60-hour*60);
	    long s=(l/1000-day*24*60*60-hour*60*60-min*60);
	    sb.append(day*60*24 + hour*60 + min);
	    //System.out.println(+day+"天"+hour+"小时"+min+"分"+s+"秒");
		return Integer.valueOf(sb.toString());
	}

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1,Date date2)
	{
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
		return days;
	}
	/**
	 * 获取两个时间时间差
	 * @param time 
	 * @param now
	 * @return 返回时间差 单位为m
	 */
	public static long getTimeDiff(String time,String now){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timeDiff = 0l;
		try {
			Date t1 = df.parse(time);
			Date t2 = df.parse(now);
			timeDiff = t2.getTime() - t1.getTime();
			timeDiff = timeDiff / (1000 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeDiff;
	}
	/**
	 * 日期转换字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String str = df.format(date);
		 return str;
	}
	/**
	 * 日期转换字符串
	 * @param date
	 * @return
	 */
	public static String dateToStringYMD(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String str = df.format(date);
		return str;
	}
	/**
	 * 日期转换字符串
	 * @param date
	 * @return
	 */
	public static String dateToStringYMDHM(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = df.format(date);
		return str;
	}
	/**
	 * 日期转换字符串
	 * @param date
	 * @return
	 */
	public static String dateToStringHM(Date date){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String str = df.format(date);
		return str;
	}
	/**
	 * 日期转换字符串
	 * @param date
	 * @return
	 */
	public static String dateToStringU(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		 String str = df.format(date);
		 return str;
	}
	/**
	 * 字符串日期格式转换
	 * @param dateStr
	 * @return
	 */
	public static String parFormat(String dateStr){
        Date date = new Date(); 
        //注意format的格式要与日期String的格式相匹配 
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
        try { 
            date = sdf.parse(dateStr);
            return df.format(date);
        } catch (Exception e) { 
            e.printStackTrace();
        }
		return null;
	}
	/**
	 * 获取注册时间(yyyyMMddHHmmss格式)
	 * @param regtime
	 * @return
	 */
	public static String getRegisterDate(String regtime){
		String d = regtime;
		d = d.replace("-", "");
		d = d.replace(":", "");
		d = d.replace(" ", "");
		return d;
	}

	/**
	 * 日期计算
	 * @param date 传入的日期
	 * @param day 要增加的天数
	 * @return
	 */
	public static Date getDateAddDay(Date date,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 字符串转换日期
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date){
		Date dateTime = null;
		try{
			SimpleDateFormat simpleFormat = new SimpleDateFormat(datePatten);
			dateTime = simpleFormat.parse(date);
		}catch (Exception e){
			e.printStackTrace();
		}
		return dateTime;
	}

	/**
	 * 日期转换字符串
	 * @param date
	 * @return
	 */
	public static String formateDate(Date date){
		String dateTime = null;
		try{
			SimpleDateFormat simpleFormat = new SimpleDateFormat(datePatten);
			dateTime = simpleFormat.format(date);
		}catch (Exception e){
			e.printStackTrace();
		}
		return dateTime;
	}

	/**
	 * 日期转换
	 * @param date
	 * @return
	 */
	public static Date formateSimpleDate(String date){
		Date dateTime = null;
		try{
			SimpleDateFormat simpleFormat = new SimpleDateFormat(simple);
			dateTime = simpleFormat.parse(date);
		}catch (Exception e){
			e.printStackTrace();
		}
		return dateTime;
	}

	/**
	 * 获取日期所在周的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int d = 0;
		if(cal.get(Calendar.DAY_OF_WEEK)==1){
			d = -6;
		}else{
			d = 2-cal.get(Calendar.DAY_OF_WEEK);
		}
		//所在周开始日期
		cal.add(Calendar.DAY_OF_WEEK, d);
		return  cal.getTime();
	}

	/**
	 * 获取日期所在周的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int d = 0;
		if(cal.get(Calendar.DAY_OF_WEEK)==1){
			d = -6;
		}else{
			d = 2-cal.get(Calendar.DAY_OF_WEEK);
		}
		//所在周开始日期
		cal.add(Calendar.DAY_OF_WEEK, d);
		cal.add(Calendar.DAY_OF_WEEK, 6);
		//所在周结束日期
		return cal.getTime();
	}

	/**
	 * 获取日期所在月的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return  cal.getTime();
	}

	/**
	 * 获取日期所在月的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static void main(String[] args) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(format.format(getFirstDayOfMonth(new Date())));
		System.out.println(format.format(getLastDayOfMonth(new Date())));
		System.out.println(format.format(getFirstDayOfMonth(format.parse("2016-02-23"))));
		System.out.println(format.format(getLastDayOfMonth(format.parse("2016-02-23"))));

	}
}
