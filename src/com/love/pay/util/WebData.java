package com.love.pay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class WebData {

	private static Logger log = Logger.getLogger(WebData.class);
	private final static int TIMEOUT = 30000;
	
	private static long JISHIQI = 0l;
	private final static ConcurrentHashMap<String, Long> map_data = new ConcurrentHashMap<String, Long>();
	
	public WebData() {

	}

	public static String postRequest(String url,String paras) {
		log.debug("\nWebData postRequest: " + url + "\n" + paras);
		try {
			long t1_start = System.currentTimeMillis();
			
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			long t1 = System.currentTimeMillis();
			connection.getOutputStream().write(paras.getBytes("UTF-8"));
/*			if(paras.contains("14963256")||paras.contains("14179031")){//
				String content2 = "\nug_sendmsg:connection.getOutputStream().write: "+url + "\n" + paras + connection.getResponseCode()+14963256;
				BaseAction.addExeTime(t1, 14963256, content2);
			}*/
			t1 = System.currentTimeMillis();
			InputStreamReader inputr = new InputStreamReader(connection.getInputStream(), "UTF-8");
/*			if(paras.contains("14963256")||paras.contains("14179031")){//
				String content2 = "\nug_sendmsg:new InputStreamReader(connection.getInputStream(): "+url + "\n" + paras + connection.getResponseCode()+14963256;
				BaseAction.addExeTime(t1, 14963256, content2);
			}*/
			t1 = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(inputr);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
/*			if(paras.contains("14963256")||paras.contains("14179031")){//
				String content2 = "\nug_sendmsg:new BufferedReader(inputr): "+connection.getResponseCode()+14963256;
				BaseAction.addExeTime(t1, 14963256, content2);
			}*/
			log.debug("\nWebData getRequest response: " + buffer.toString());
			inputr.close();
			reader.close();
			connection.disconnect();
			
			//addExeTimeForAll(t1_start, url);
			return buffer.toString();
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String postJsonRequest(String url,String paras,Map<String,String>... headerMap) {
		log.debug("\nWebData postRequest: " + url + "\n" + paras);
		try {
			long t1_start = System.currentTimeMillis();
			
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			if (headerMap.length>0){
				for (String key : headerMap[0].keySet()){
					connection.setRequestProperty(key, headerMap[0].get(key));
				}
			}

			long t1 = System.currentTimeMillis();
			connection.getOutputStream().write(paras.getBytes("UTF-8"));
/*			if(paras.contains("14963256")||paras.contains("14179031")){//
				String content2 = "\nug_sendmsg:connection.getOutputStream().write: "+url + "\n" + paras + connection.getResponseCode()+14963256;
				BaseAction.addExeTime(t1, 14963256, content2);
			}*/
			t1 = System.currentTimeMillis();
			InputStreamReader inputr = new InputStreamReader(connection.getInputStream(), "UTF-8");
/*			if(paras.contains("14963256")||paras.contains("14179031")){//
				String content2 = "\nug_sendmsg:new InputStreamReader(connection.getInputStream(): "+url + "\n" + paras + connection.getResponseCode()+14963256;
				BaseAction.addExeTime(t1, 14963256, content2);
			}*/
			t1 = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(inputr);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
/*			if(paras.contains("14963256")||paras.contains("14179031")){//
				String content2 = "\nug_sendmsg:new BufferedReader(inputr): "+connection.getResponseCode()+14963256;
				BaseAction.addExeTime(t1, 14963256, content2);
			}*/
			log.debug("\nWebData getRequest response: " + buffer.toString());
			inputr.close();
			reader.close();
			connection.disconnect();
			
			//addExeTimeForAll(t1_start, url);
			return buffer.toString();
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static String getRequest(String url) {
		log.debug("\nWebData getRequest: " + url);
		try {
			long t1_start = System.currentTimeMillis();
			
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			connection.setDoOutput(false);
			connection.setRequestMethod("GET");
			InputStreamReader inputr = new InputStreamReader(connection.getInputStream(), "UTF-8");
			BufferedReader reader = new BufferedReader(inputr);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
			log.debug("\nWebData getRequest response: " + buffer.toString());
			inputr.close();
			reader.close();
			connection.disconnect();
			
			//addExeTimeForAll(t1_start, url);
			return buffer.toString();
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	
/*	public static void addExeTimeForAll(Long t1,String u){
		//map_data.put(key, value)
		String url1 = subByW(subByX(u));

		long t2 = System.currentTimeMillis();
		long t21b = t2-t1;
		
		if(map_data.containsKey(url1)){
			Long tb = map_data.get(url1);
			tb = tb+t21b;
			map_data.put(url1, tb);
			String cishu_key = "`"+url1;
			Long cishu = map_data.get(cishu_key);
			cishu = cishu+1;
			map_data.put(cishu_key, cishu);
		}else{
			map_data.put(url1, t21b);
			String cishu_key = "`"+url1;
			map_data.put(cishu_key, 1l);
		}
		
		if(((t2-JISHIQI)/1000l)>300){
			JISHIQI = t2;
			log.error(new Gson().toJson(map_data));
		}
		
	}*/
	
	public static String subByX(String c){
		int cLength = c.length();
		int a = c.indexOf("/", 8);
		String subC = null;
		if (a > 0) {
			int b = c.indexOf("/", a + 1);
			if (b > 0) {
				int d = c.indexOf("/", b + 1);
				if (d > 0) {
					subC = c.substring(a, d);
				} else {
					subC = c.substring(a, cLength);
				}
			} else {
				subC = c.substring(a, b);
			}
		} else {
			subC = c.substring(a, cLength);
		}

		//System.out.println(subC);
		return subC;
	}
	
	public static String subByW(String c){
		int a = c.indexOf("?");
		String subC = null;
		if (a > 0) {
			subC = c.substring(0, a);
			return subC;
		} 
		return c;
	}

	/***
	 * 从request获取body体内容
	 * @param request
	 * @return
	 */
	public static String requestBodyForJson(HttpServletRequest request) {
		try {
			byte[] bytes = new byte[1024 * 1024];
			InputStream is = request.getInputStream();
			int nRead = 1;
			int nTotalRead = 0;
			while (nRead > 0) {
				nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
				if (nRead > 0)
					nTotalRead = nTotalRead + nRead;
			}
			String json = new String(bytes, 0, nTotalRead, "utf-8");
			is.close();
			return json;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String requestBodyForJsonFromInputStream(InputStream is) {
		try {
			byte[] bytes = new byte[1024 * 1024];
			int nRead = 1;
			int nTotalRead = 0;
			while (nRead > 0) {
				nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
				if (nRead > 0)
					nTotalRead = nTotalRead + nRead;
			}
			String json = new String(bytes, 0, nTotalRead, "utf-8");
			is.close();
			return json;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getRequest(String url,Map<String,String> cookies) {
		log.debug("\nWebData getRequest: " + url);
		try {
			long t1_start = System.currentTimeMillis();
			
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			connection.setDoOutput(false);
			connection.setRequestMethod("GET");
			StringBuffer sbCookie = new StringBuffer();
			for(Entry<String, String> entry : cookies.entrySet()){
				sbCookie.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
			}
			connection.setRequestProperty("Cookie",sbCookie.toString());
			InputStreamReader inputr = new InputStreamReader(connection.getInputStream(), "UTF-8");
			BufferedReader reader = new BufferedReader(inputr);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
			log.debug("\nWebData getRequest response: " + buffer.toString());
			inputr.close();
			reader.close();
			connection.disconnect();
			
			//addExeTimeForAll(t1_start, url);
			return buffer.toString();
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static void main(String[] arg){
		System.out.println(WebData.postJsonRequest("http://127.0.0.1:8081/msg/101", "{\"c\":200,\"id\":1,\"info\":null,\"d\":{\"age\":111,\"username\":\"yuruyi\"}}", new HashMap<String, String>()));
	}

	


}
