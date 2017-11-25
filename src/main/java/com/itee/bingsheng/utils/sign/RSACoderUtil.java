package com.itee.bingsheng.utils.sign;

import com.itee.bingsheng.pay.base.utils.http.HttpClientUtil;
import com.itee.bingsheng.utils.StringTools;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Args;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * RSA安全编码组件
 *  
 * @version 1.0
 * @since 1.0
 */
public abstract class RSACoderUtil extends CoderUtil {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static ResourceBundle config = ResourceBundle.getBundle("config");
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	private static final String CHANNEL_ID = "qd_huliCS";
	private static final String CHANNEL_SECRET = "TDGklkkkpk1646hfjfglmg4665op";

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            签名数据
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return encryptBASE64(signature.sign());
	}


	public static String sign(String data) throws Exception {
		return sign(data.getBytes(), config.getString("channel.privateKey"));
	}
	
	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
	}
	public static boolean verify(String data, String sign) throws Exception {
      boolean flag= false;
		try{
			flag =verify(data.getBytes(), config.getString("channel.publicKey"), sign);

		}catch (Exception e){
			e.printStackTrace();
		}

		return flag;
	}
	
	/**
	 * 解密<br>
	 * 用私钥解密 http://www.5a520.cn http://www.feng123.com
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(config.getString("channel.privateKey"));

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(config.getString("channel.publicKey"));

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data) throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(config.getString("channel.publicKey"));

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(config.getString("channel.privateKey"));

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**泛华保网HTTP请求
	 * @param reqURL
	 * @param postJson
	 * @return
	 * @throws Exception
	 */
	public static String sendPostByRSA(String reqURL, String  postJson)throws Exception{
		//随机字符串（32~40位大小写数字组成）
		String nonceStr = StringTools.getRandomString(32);
		//获取accessToken
		Map<String, Object> tokenParams=new HashMap<String, Object>();
		tokenParams.put("channelId",CHANNEL_ID);
		tokenParams.put("channelSecret",CHANNEL_SECRET);
		String token = HttpClientUtil.sendPostRequestString2(config.getString("channel.getToken"), JSONObject.fromObject(tokenParams).toString(), "utf-8");
		Args.notNull(token,"getToken");
		if (StringUtils.isNotEmpty(token)) {
			JSONObject jo=JSONObject.fromObject(token);
			String accessToken=jo.getString("accessToken");
			//将Token和随机串拼接成一个字符串(Token在前，随机串在后)
			String str=accessToken+nonceStr;
			//用私钥对拼接后的字符串进行签名(MD5withRSA)
			String signStr = sign(str.getBytes(), config.getString("channel.privateKey"));

			Map<String, Object> headerMap=new HashMap<String, Object>();
			headerMap.put("channelId",CHANNEL_ID);
			headerMap.put("nonceStr",nonceStr);
			headerMap.put("signStr", StringTools.replaceBlank(signStr));
			return HttpClientUtil.sendPostRequestString3(reqURL, postJson, "utf-8",headerMap);
		}
		return null;
	}
	public static void main1(String[] args) throws Exception {
//		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN90tXnUT20Qtm+I3dbKS7QQrs2l" +
//				"o8QcPpspR6G9WZeGjxSTRnkX3hdLIjmaiehXffC8PeYGAUXn/rdblnCSQrm4FdpWEY//d0rQSgGd" +
//				"8cs2t8Kpl4DvbP/+qGBxrVD3Q7Smc0J35pTArNOgwy6Sl4k3h7V0hRIZjfxXOkAzO5zJAgMBAAEC" +
//				"gYEAgu+eTy8LA3uhiyWF6BBN38tOwo3msklingTIRovvbYyZVpMd3mMP7lJGUb6uRIjP8To8gwbN" +
//				"xCq25LY0Ju5tTdGl2lKiUbRXE8zUi9vZwd6WfXYi3WWmTBYjcoVqeWsI8tlx5o/swzN6lFYvs2eC" +
//				"Hfs0Drs2R+Ta56gbIxIGkCECQQD0VIZBSoKuXbO6x+2zSzkPzbSa9qi2OlCatv+b0WJjCZHmDwAB" +
//				"P4lOtkpKOxtSnXsMqQ0Is7lX4NTLedRAyuMNAkEA6iDzSG/vLj5Eb4OzJUpIxFEn56sMFX7GuDi6" +
//				"6xP6D8qiupmrjXFHrjAifCSF6nbkaga1puojVqL6b1I4NWmhrQJBAIK2OYDykMkh3gZd8T/LTYKz" +
//				"5RxGO2oJ9pdesY61zPH467Htcm44hIe0pDfkOTDQiUTzp8JxDAYEhTM6QSBMqn0CQAsFZA5b3olx" +
//				"uuz46RzvQz+ihltcbOQyJI6VdQ8N0K6fnktkYnP1CifD8kufuIIR+KyZBkIGMYWphFprJ2Q0Rb0C" +
//				"QCm4ONB07E/e8/bneyvck3mzyT11dWEIt5a2YSAvqXxXwLvcfbdEvSjMG0Z9UHN4bK9FEtv+KmYB" +
//				"Tg8w1My9Swo=";
//
//		String str = "TLbbzQOcdq1eZVIkS7JHUo1knGgAQWex3fOBk935ZSmT9eVJQASOy7MESMLKnZDF";
//		System.out.println("签名内容：" + str);
//		String signStr = sign(str.getBytes(), privateKey);
//		System.out.println("签名结果：" + signStr);
//
//		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfdLV51E9tELZviN3Wyku0EK7NpaPEHD6bKUeh" +
//				"vVmXho8Uk0Z5F94XSyI5monoV33wvD3mBgFF5/63W5ZwkkK5uBXaVhGP/3dK0EoBnfHLNrfCqZeA" +
//				"72z//qhgca1Q90O0pnNCd+aUwKzToMMukpeJN4e1dIUSGY38VzpAMzucyQIDAQAB";
//
//		boolean b = verify(str.getBytes(), publicKey, signStr);
//		System.out.println("验签结果：" + b);

//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("RSAPrivateKey","TDGklkkkpk1646hfjfglmg4665op");
//		String RSAPrivateKey=getPrivateKey(map);
//		System.out.println("RSAPrivateKey:"+RSAPrivateKey);


//		String str = "pSdFY2LW4aLZ7Vcr1A6hVLuycAQp6Nl23fOBk935ZSmT9eVJQASOy7MESMLKnZDF";
//		System.out.println("签名内容：" + str);
//		String signStr = sign(str);
//		System.out.println("签名结果：" + signStr);

		Map<String, Object> map=initKey();
		String RSAPrivateKey=getPrivateKey(map);
		System.out.println("RSAPrivateKey:"+RSAPrivateKey);
		String nonceStr = StringTools.getRandomString(32);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("channelId",CHANNEL_ID);
		params.put("channelSecret",CHANNEL_SECRET);
		String token = HttpClientUtil.sendPostRequestString2("http://icm.uat.52zzb.com/cm/channelService/getToken", JSONObject.fromObject(params).toString(), "utf-8");
		System.out.println("token:"+token);
		if (StringUtils.isNotEmpty(token)) {
			JSONObject jo=JSONObject.fromObject(token);
			String accessToken=jo.getString("accessToken");
			String str=accessToken+nonceStr;
			System.out.println("签名内容：" + str);
			String signStr = sign(str.getBytes(), RSAPrivateKey);
			System.out.println("签名结果：" + signStr);

			Map<String, Object> params1=new HashMap<String, Object>();
			params.put("agreementProvCode","440000");
			Map<String, Object> headerMap=new HashMap<String, Object>();
			headerMap.put("channelId",CHANNEL_ID);
			headerMap.put("nonceStr",nonceStr);
			headerMap.put("signStr", StringTools.replaceBlank(signStr));
			String result = HttpClientUtil.sendPostRequestString3("http://icm.uat.52zzb.com/cm/channelService/getAgreementAreas", JSONObject.fromObject(params1).toString(), "utf-8",headerMap);
			System.out.println(result);
		}

	}

	public static void main(String[] args)throws Exception{
		Map<String, Object> keyMap = initKey();

		String publicKey = getPublicKey(keyMap);
		String privateKey = getPrivateKey(keyMap);
		System.err.println("公钥: \n\r" + publicKey);
		System.err.println("私钥： \n\r" + privateKey);
	}
}