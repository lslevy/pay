package com.love.pay.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/**P3P**/
	public final static String P3P = "CP=\"NON DSP COR CURa ADMa DEVa TAIa PSAa PSDa IVAa IVDa CONa HISa TELa OTPa OUR UNRa IND UNI COM NAV INT DEM CNT PRE LOC\"";	

	/**
	 * 设置cookie
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, String domain, Integer max_age) {
		Cookie cookie = new Cookie(key,value);
		cookie.setPath("/");
		if(domain!=null){
			cookie.setDomain(domain);
		}
		if(max_age!=null){
			cookie.setMaxAge(max_age);
		}
		//cookie.setHttpOnly(true);//js或其它程序中获取不到
		response.setHeader("P3P", P3P);  	
		response.addCookie(cookie);
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



}
