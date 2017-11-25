package com.itee.bingsheng.utils;

import com.alibaba.fastjson.JSONObject;
import niyo.com.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * 微信官方MD5算法
 */
public class MD5Util {

	//种子源 uuid自定义生成
	private  static final String salt = "5A1FE400499C46A5BA723CD8E85247E5";

	public MD5Util() {
	}

	public static String ecodeByMD5(String originstr) {
		String result = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		if (originstr != null) {
			try {
				// 返回实现指定摘要算法的 MessageDigest 对象
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用utf-8编码将origins
				byte[] source = originstr.getBytes("utf-8");
				// 使用指定的 byte 数组更新摘要
				md.update(source);
				// 通过执行诸如填充之类的最终操作完成哈希计算，结果是一个128位的长整数
				byte[] tmp = md.digest();
				// 用16进制数表示需要32位
				char[] str = new char[32];
				for (int i = 0, j = 0; i < 16; i++) {
					// j表示转换结果中对应的字符位置
					// 从第一个字节开始，对 MD5 的每一个字节
					// 转换成 16 进制字符
					byte b = tmp[i];
					// 取字节中高 4 位的数字转换
					// 无
					// 0x代表它后面的是十六进制的数字. f转换成十进制就是15
					str[j++] = hexDigits[b >>> 4 & 0xf];
					// 取字节中低 4 位的数字转换
					str[j++] = hexDigits[b & 0xf];
				}
				result = new String(str);// 结果转换成字符串用于返回
			} catch (NoSuchAlgorithmException e) {
				// 当请求特定的加密算法而它在该环境中不可用时抛出此异常
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// 不支持字符编码异常
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 默认加盐加密
	 * @Des 得到相应的一个MD5加密后的字符串
	 * @return    MD5加密后的字符串
	 */
	public static String ecodeByMD5AndSalt(String psd) {
		try {
			StringBuffer stingBuffer = new StringBuffer();
			// 1.指定加密算法
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// 2.将需要加密的字符串转化成byte类型的数据，然后进行哈希过程
			byte[] bs = digest.digest((psd + salt).getBytes());
			// 3.遍历bs,让其生成32位字符串，固定写法

			// 4.拼接字符串
			for (byte b : bs) {
				int i = b & 0xff;
				String hexString = Integer.toHexString(i);
				if (hexString.length() < 2) {
					hexString = "0" + hexString;
				}
				stingBuffer.append(hexString);
			}
			return stingBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 传入当前用户的密码明文和数据库里面的密码进行验证
	 * @param pwd
	 * @param password
	 * @return
	 */
	private static boolean isValid(String pwd,String password){
		String newpd=MD5Util.ecodeByMD5AndSalt(pwd);
		if(newpd.equals(password)){
			return true;
		}else {
			return false;
		}
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 *原生md5加密
	 * @param origin
	 * @return
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception exception) {
		}
		return resultString;
	}

	public static void main(String agrs[]) {
		String s1=MD5Util.MD5Encode("TLbbzQOcdq1eZVIkS7JHUo1knGgAQWex3fOBk935ZSmT9eVJQASOy7MESMLKnZDF");
		System.out.println("不加盐"+s1);
//		String ss= CreateUUIDUtil.createUUID();
//		System.out.println("ssssssssssssssssss"+ss);
		String s2=MD5Util.ecodeByMD5AndSalt("TLbbzQOcdq1eZVIkS7JHUo1knGgAQWex3fOBk935ZSmT9eVJQASOy7MESMLKnZDF");
		System.out.println("加盐"+s2);


	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
	/**
	 * RSA生成签名字符串
	 * @param map 需签名参数
	 * @param prikey rsa私钥
	 * @return
	 */
	public static String RSAsign(Map<String, Object> map, String prikey) {
		String genSign = "";
		try {

			String[] signFields = new String[5];
			signFields[0] = "name";
			signFields[1] = "age";
			signFields[2] = "sex";
			signFields[3] = "school";
			signFields[4] = "address";
			JSONObject param = (JSONObject) JSONObject.toJSON(map);
			// 生成签名原文
			String src = orgSignSrc(signFields, param);
//			genSign = RsaUtil.sign(src, prikey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genSign;
	}
	/**
	 * 构建签名原文
	 * @param signFields 参数列表
	 * @param param 参数与值的jsonbject
	 * @return
	 */
	private static String orgSignSrc(String[] signFields, JSONObject param) {
		if (signFields != null) {
			Arrays.sort(signFields); // 对key按照 字典顺序排序
		}
		StringBuffer signSrc = new StringBuffer("");
		int i = 0;
		for (String field : signFields) {
			signSrc.append(field);
			signSrc.append("=");
			signSrc.append((StringUtils.isEmpty(param.getString(field)) ? ""
					: param.getString(field)));
			// 最后一个元素后面不加&
			if (i < (signFields.length - 1)) {
				signSrc.append("&");
			}
			i++;
		}
		return signSrc.toString();
	}

}
