package com.itee.bingsheng.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 */
public class Encrypt {

	public static final Charset SYSTEM_CODE = Charset.forName("UTF-8");

	private static byte[] kbytes = "PBEWithMD5AndDES".getBytes(SYSTEM_CODE);

	private static String KEY = "Blowfish";

	public static String encrypt(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		SecretKeySpec key = new SecretKeySpec(kbytes, KEY);

		Cipher cipher = Cipher.getInstance(KEY);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encoding = cipher.doFinal(secret.getBytes(SYSTEM_CODE));
		BigInteger n = new BigInteger(encoding);
		return n.toString(16);
	}

	public static String decrypt(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		SecretKeySpec key = new SecretKeySpec(kbytes, KEY);

		System.out.println(secret+" ---------------------");
		BigInteger n = new BigInteger(secret, 16);
		byte[] encoding = n.toByteArray();

		Cipher cipher = Cipher.getInstance(KEY);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decode = cipher.doFinal(encoding);
		return new String(decode);
	}

	public static void main(String[] args) {
		String passwd = "123456abcdef";
		try {
			String encryptpwd = encrypt(passwd);
			System.out.println(encryptpwd);

			String unencyppwd = decrypt(encryptpwd);
			System.out.println(unencyppwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
