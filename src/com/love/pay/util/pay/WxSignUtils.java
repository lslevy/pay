package com.love.pay.util.pay;

import com.love.pay.entity.xml.WxPayXml;
import com.love.pay.util.CommonUtil;

import java.util.*;

/**
 * Created by j_15 on 2017/1/15.
 */
public class WxSignUtils {
    //定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static SortedMap beanToMap(WxPayXml pay){
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        if(null!=pay.getAppid()) {
            signParams.put("appid", pay.getAppid());//app_id
        }
        if(null!=pay.getAttach()) {
            signParams.put("attach", pay.getAttach());//attach
        }
        if(null!=pay.getBody()) {
            signParams.put("body", pay.getBody());//商品参数信息
        }
        if(null!=pay.getMah_id()) {
            signParams.put("mch_id", pay.getMah_id());//微信商户账号
        }
        if (null != pay.getNonce_str()) {
            signParams.put("nonce_str", pay.getNonce_str());//32位不重复的编号
        }
        if (null != pay.getNotify_url()) {
            signParams.put("notify_url", pay.getNotify_url());//回调页面
        }
        if (null != pay.getOut_trade_no()) {
            signParams.put("out_trade_no", pay.getOut_trade_no());//订单编号
        }
        if (null != pay.getSpbill_create_ip()) {
            signParams.put("spbill_create_ip", pay.getSpbill_create_ip());//请求的实际ip地址
        }
        if (null != pay.getTotal_fee()) {
            signParams.put("total_fee", pay.getTotal_fee() + "");//支付金额 单位为分
        }
        if (null != pay.getTrade_type()) {
            signParams.put("trade_type", pay.getTrade_type());//付款类型为APP
        }
        return signParams;
    }


    //定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static String createSign(String characterEncoding,SortedMap<String,String> parameters,String key){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        String sign = CommonUtil.getMD5String(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    //将封装好的参数转换成Xml格式类型的字符串

    public static String getRequestXml(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if("sign".equalsIgnoreCase(k)){

            }
            else if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }
            else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        //sb.append("<"+"sign"+">"+"<![CDATA["+parameters.get("sign")+"]]></"+"sign"+">");
        sb.append("<sign>"+parameters.get("sign")+"</sign>");
        sb.append("</xml>");
        return sb.toString();
    }
}
