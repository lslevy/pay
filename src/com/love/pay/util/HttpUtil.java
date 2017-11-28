package com.love.pay.util;


import com.love.pay.dto.Orders;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liusheng on 2017/2/24.
 */
public class HttpUtil {
    public static Orders getOrdersData(String orders_id) {
        String ADD_URL =CustomizedPropertyConfigurer.getCtxProp("orders_get_url")+"&orders_id="+orders_id;
        //String ADD_URL ="http://localhost:8080/msg/704?debugAccountId=1&orders_id=30720170317102431";
        Orders orders = new Orders();
        String result = "";
        try {
            //创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "UTF-8");
                sb.append(lines);
            }
            System.out.println(sb);

            JSONObject obj = JSONObject.fromObject(sb.toString());
            orders.setId(obj.getLong("id"));
            orders.setOrder_id(obj.getString("order_id"));
            orders.setMoney(Float.parseFloat(obj.getString("money")));
            orders.setMoney_str(obj.getString("money_str"));
            orders.setAccount_id(obj.getLong("account_id"));
            orders.setChannel(obj.getString("channel"));
            orders.setStatus(obj.getInt("status"));
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="00";
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="00";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="00";
        }
        return orders;

    }


    public static String sendPostObj(String channel_order_id,Orders order,String ADD_URL) {
        String result = "01";
        String result_data = "";
        try {
            //创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject ordersJson = new JSONObject();
            ordersJson.put("channel_order_id", channel_order_id);
            ordersJson.put("id", order.getId().toString());
            ordersJson.put("money", order.getMoney_str());
            ordersJson.put("account_id", order.getAccount_id());
            ordersJson.put("state", "1");

            out.write(ordersJson.toString().getBytes("UTF-8"));
            out.flush();
            out.close();
            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "UTF-8");
                sb.append(lines);
            }
            System.out.println(sb);
            result_data = sb.toString();
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="00";
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="00";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="00";
        }

        return result_data;

    }

    public static void main(String[] args) {

        //http://localhost:8080/msg/704?debugAccountId=1
        //sendPostZFBOrder("30720170317102431");
        //localhost: 30720170317102431
        //online:  17720170317105547
 //       getOrdersData("17720170317105547");
//        String str = "1";
//        Double d = Double.parseDouble(str)*100;
//        Integer b=d.intValue();
//        System.out.println("Double:"+d);
//        System.out.println("Integer:"+b);

    }
}
