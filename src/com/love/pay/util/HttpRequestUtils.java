package com.love.pay.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by j_15 on 2016/11/30.
 */
public class HttpRequestUtils {

    private static Logger logger = Logger.getLogger(HttpRequestUtils.class);    //日志记录

    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam){
        return httpPost(url, jsonParam, false);
    }

    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (Exception e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url){
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.parseObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (Exception e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * 向指定 URL 发送请求
     *
     * @param method  请求方式 GET/POST
     * @param url     发送请求的 URL
     * @param content 请求参数 JSON对象的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendHttp(String method, String url, String content) {
        String message = "";
        try {
            System.out.println("请求的URL：" + url);
            System.out.println("请求的CONTENT：" + content);
            URL url_con = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url_con
                    .openConnection();
            conn.setRequestMethod(method.toUpperCase());
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("platform", "test");
            conn.setRequestProperty("version", "test");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
//            conn.setConnectTimeout(30000);
//            conn.setReadTimeout(30000);
            conn.connect();
            if ("POST".equalsIgnoreCase(method)) {
                OutputStream output = conn.getOutputStream();
                output.write(content.getBytes("UTF-8"));
                output.flush();
                output.close();
            }
            InputStream input = conn.getInputStream();
            int size = input.available();
            byte[] bytes = new byte[size];
            input.read(bytes);
            message = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送请求异常：" + e);
        }
        System.out.println("请求的结果：" + message);
        return message;
    }
    public static void main(String[] args) {
        System.err.println(sendHttp("post","http://118.89.150.168:8080/msg/100","{}"));
        //System.err.println(httpPost("http://118.89.150.168:8080/msg/100", new JSONObject()));
    }


}
