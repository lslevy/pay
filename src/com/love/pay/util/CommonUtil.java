package com.love.pay.util;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;

/**
 * Created by ruyi.yu on 2016/1/19.
 */
public class CommonUtil {

	/**
	 * 获取ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * 判断是否为空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean empty(String str) {
		if (str == null || "".equals(str.trim()) || !StringUtils.hasLength(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Object depClone(Object srcObj) {
		Object cloneObj = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
			objectOutputStream.writeObject(srcObj);

			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(in);
			cloneObj = objectInputStream.readObject();

			objectOutputStream.close();
			objectInputStream.close();
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloneObj;
	}

    /**随机字符*/
	public static final char[] charArr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

	/**
	 * 生成给定长度的随机数
	 */
	public static String getRandomString(int length){
		Random random = new Random();
		char[] newCharArr = new char[length];
		for (int i = 0; i < newCharArr.length; i++) {
			newCharArr[i] = charArr[random.nextInt(charArr.length)];
		}
		return new String(newCharArr);
	}

	/**
	 * 获取md5值
	 */
	public static String getMD5String(String s, String ... charsetName){
		String md5Str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetName.length>0)
				md.update(s.getBytes(charsetName[0]));
			else md.update(s.getBytes());

			byte b[] = md.digest();
			int i;

			StringBuffer buf = new StringBuffer();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			md5Str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}

	public static void main(String[] args) {
		getMD5String("abc");
	}

	/**
	 * 获取cookie
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name){
		Cookie[] cookie = request.getCookies();
		if(cookie!=null){
			for(int i=0;i<cookie.length;i++){
				Cookie cook=cookie[i];
				if(name.equals(cook.getName())){
					return cook;
				}
			}
		}
		return null;
	}

	/***
	 * 获取url地址
	 * @param request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request){
		String requestUri = request.getRequestURI();
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme()).append("://").append(request.getHeader("host")).append(requestUri);
		if (request.getQueryString() != null) {
			sb.append("?").append(request.getQueryString());
		}
		return sb.toString();
	}

	/**
	 * 输出json数据
	 * @param response
	 * @param object
	 */
	public static void printJSON(HttpServletResponse response, Object object) {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSON.toJSON(object));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
				}
			}
			out = null;
			response = null;
		}

	}


	/**
	 * 将url参数转换成map
	 * @param param aa=11&bb=22&cc=33
	 * @return
	 */
	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (empty(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	/**
	 * 将map转换成url
	 * @param map
	 * @return
	 */
	public static String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	/**
	 * 将排序map转换成url
	 * @param map
	 * @return
	 */
	public static String getSortUrlParamsByMap(SortedMap<String, String> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

}
