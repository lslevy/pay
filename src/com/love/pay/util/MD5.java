package com.love.pay.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * MD5加密
 */
public class MD5 {

	private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	MessageDigest messageDigest;

	public MD5() {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param source
	 * @return
	 */
	public byte[] doFinal(byte[] source) {
		return messageDigest.digest(source);
	}

	/**
	 * 加密
	 * 
	 * @param source
	 * @return
	 */
	public String doFinalHex(byte[] source) {
		return toHex(messageDigest.digest(source));
	}

	/**
	 * 加密
	 * 
	 * @param source
	 * @return
	 */
	public String doFinalHex(String source) {
		byte[] sourceBs;
		try {
			sourceBs = source.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			sourceBs = source.getBytes();
		}
		return doFinalHex(sourceBs);
	}

	/**
	 * 字节转HEX字符串
	 * 
	 * @param hash
	 * @return
	 */
	public static String toHex(byte[] hash) {
		char buf[] = new char[hash.length * 2];
		for (int i = 0, x = 0; i < hash.length; i++) {
			buf[x++] = HEX_CHARS[(hash[i] >>> 4) & 0xf];
			buf[x++] = HEX_CHARS[hash[i] & 0xf];
		}
		return new String(buf);
	}

	/**
	 * 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String doFinal(String source) {
		return new MD5().doFinalHex(source).toLowerCase();
	}

}
