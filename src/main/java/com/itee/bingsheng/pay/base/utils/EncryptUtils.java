package com.itee.bingsheng.pay.base.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 常用加密算法工具类
 * @author hxb
 *
 */
public class EncryptUtils
{

	/**
	 * 用MD5算法进行加密
	 * @param 需要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String md5(String str)
	{
		return encode(str, "MD5");
	}

	/**
	 * 用SHA算法进行加密
	 * @param 需要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String sha(String str)
	{
		return encode(str, "SHA");
	}

	/**
	 * 用base64算法进行加密
	 * @param 需要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String base64Encode(String str)
	{
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str.getBytes());
	}

	/**
	 * 用base64算法进行解密
	 * @param 需要加密的字符串
	 * @return 加密后的字符串
	 * @throws IOException
	 */
	public static String base64Decode(String str) throws IOException
	{
		BASE64Decoder encoder = new BASE64Decoder();
		return new String(encoder.decodeBuffer(str));
	}

	/**
	 * 加密算法
	 * @param 需要加密的字符串
	 * @param 加密方法
	 * @return 加密后的字符串
	 */
	private static String encode(String str, String method)
	{
		MessageDigest md = null;
		String dstr = null;
		try
		{
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			//dstr = new BigInteger(1,md.digest()).toString(16);//这种方式会出现31位的情况，不使用
			dstr = byteArrayToHex(md.digest());

		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return dstr;
	}

	/**
	 *
	 * @param byteArray
	 * @return
	 */
	private static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray =new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b& 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

	public static void main(String[] args) throws IOException
	{

//		String signData = "appid=wxc0ad89d0e0da22c8&body=上飘&mch_id=1239851302&nonce_str=KJ371V9X98YFVOZ0HBAYHT358F2YWE&notify_url=http://183.62.138.52:8126/&out_trade_no=14563621063238110&spbill_create_ip=127.0.0.1&total_fee=1&trade_type=NATIVE&key=db55aeb8ee4e79f71f513ac5ba786892";
//		System.out.println("md5加密:"+md5(signData).toUpperCase());
//		System.out.println("md5加密:"+md5(signData));
//
//		System.out.println("sha加密:"+sha("hello"));
//		System.out.println("sha加密:"+sha("中文"));
//
		String temp = base64Encode("c65eape1205o4b8efc3e278x8968c22cf8db0d62b672fecd75ee5a48fd8dfb4b3612e383bf54f031ed40b26d8756e20cafb01bf59cc4f4fb213e89b99d5b9e8b2263f629");
		System.out.println("base64加密:"+temp);
		System.out.println("base64解密:"+base64Decode(temp));

		
	}
}