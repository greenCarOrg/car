package com.itee.bingsheng.utils;

import java.io.UnsupportedEncodingException;

public class StringEncodeUtils {

	/**
	 * utf-8转成iso-88591
	 * 一般是从java中传值到jsp中，需要转化下
	 * @return
	 */
	public static String utf8ToIso88591(String str){
		String ret = "";
		try {
			ret = new String(str.getBytes("UTF-8"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * ISO-8859-1转化成utf-8
	 * 一般是从jsp中传值到java中，需要转化下
	 * @param str
	 * @return
	 */
	public static String iso88591ToUtf8(String str){
		String ret = "";
		try {
			ret = new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * gbk转成iso-8859-1
	 * 一般用在文件下载时的文件名的编码
	 * @param str
	 * @return
	 */
	public static String gbkToIso88591(String str){
		String ret = "";
		try {
			ret = new String(str.getBytes("GBK"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 转换字符串的编码
	 * @param str  字符串
	 * @param source 字符串本身编码
	 * @param replacement 转化后的编码
	 * @return
	 */
	public static String convertEncode(String str,String source,String replacement){
		String ret = "";
		try {
			ret = new String(str.getBytes(source),replacement);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 转换用户充值接口错误信息
	 * @param result  字符串
	 * @return
	 */
	public static String formatErrorCode(String result){
		String ret = "充值到钱包产生转入流水发生异常，异常原因：调用软库用户充值接口错误";
		try {
			if("-1".equals(result)){
				ret = "未找到该学生家长软库账号，请核实是否注册";
			}else if("-2".equals(result)){
				ret = "充值金额错误";
			}else if("-3".equals(result)){
				ret = "参数错误";
			}else {
				ret = "调用软库用户充值接口错误";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
