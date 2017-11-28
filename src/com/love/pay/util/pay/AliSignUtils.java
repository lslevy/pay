package com.love.pay.util.pay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.love.pay.dto.Orders;
import com.love.pay.util.CommonUtil;
import com.love.pay.util.CustomizedPropertyConfigurer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Created by j_15 on 2017/1/12.
 */
public class AliSignUtils {

    /**
     * 获取支付签名
     * @param orders
     * @param flag  true:签名 false：返回拼接签名后的url
     * @return
     * @throws AlipayApiException
     */
    public static String getSign(Orders orders, String date, boolean flag) throws AlipayApiException {
        SortedMap<String, Object> map = new TreeMap<>();

        // 公共请求参数
        map.put("app_id", CustomizedPropertyConfigurer.getCtxProp("appid"));


        // 支付业务请求参数
        JSONObject biz = new JSONObject();
        biz.put("timeout_express",CustomizedPropertyConfigurer.getCtxProp("time_out"));//非必填
        biz.put("product_code", "QUICK_MSECURITY_PAY");// 销售产品码

        //英文的
        biz.put("subject","Pay"); // 订单标题
        biz.put("body", "Pay");// 对交易或商品的描述

//        String subject ="";
//        String body ="";
//        try {
//            subject = URLEncoder.encode("上海啦唔", "UTF-8");
//            body = URLEncoder.encode("Pay", "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        biz.put("subject",subject); // 订单标题
//        biz.put("body", body);// 对交易或商品的描述



        //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]  String
        biz.put("total_amount", orders.getMoney_str());
        //biz.put("total_amount", 0.1);
        biz.put("out_trade_no",orders.getOrder_id());
//        if(order.getIs_use_balance() > 0)//使用余额
//            biz.put("total_amount",order.getPrice().subtract(order.getBalance()));
//        else
//            biz.put("total_amount",order.getPrice());

        //biz.put("seller_id",CustomizedPropertyConfigurer.getCtxProp("pid"));//非必填
        //biz.put("seller_id","");//非必填
        //biz.put("product_code","QUICK_MSECURITY_PAY");//固定值
        //biz.put("goods_type","0");//非必填//0—虚拟类商品，1—实物类商品
        //biz.put("passback_params",null);//非必填
        //biz.put("promo_params",null);//非必填//仅与支付宝协商后可用
        //biz.put("extend_params",null);//非必填//业务扩展参数
        //biz.put("enable_pay_channels","balance,moneyFund");//非必填//与disable_pay_channels互斥
        //biz.put("disable_pay_channels",null);//非必填
        map.put("biz_content", biz);
        map.put("charset", "utf-8");
        map.put("format", "json");
        map.put("method", "alipay.trade.app.pay");
        map.put("notify_url", CustomizedPropertyConfigurer.getCtxProp("ali_notify_url"));
        map.put("sign_type", "RSA2");
        map.put("timestamp", date);
        map.put("version", "1.0");

        String content = CommonUtil.getUrlParamsByMap(map);
        String sign = AlipaySignature.rsaSign(content, CustomizedPropertyConfigurer.getCtxProp("private_key"), "UTF-8", "RSA2");
        if(flag){
            return sign;
            //return content;
        }else{
            String bizEncode = URLEncoder.encode(biz.toJSONString());
            //对所有map中进行编码
            SortedMap<String, Object> encodeMap = new TreeMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if("biz_content".equals(entry.getKey())){
                    //设置英文
                    encodeMap.put(entry.getKey(), URLEncoder.encode(JSONObject.toJSONString(entry.getValue())));
                    //设置中文
                    //encodeMap.put(entry.getKey(), JSONObject.toJSONString(entry.getValue()));
                }else{
                    //设置英文
                    encodeMap.put(entry.getKey(), URLEncoder.encode(entry.getValue().toString()));
                    //设置中文 biz.put("subject","草");
                    //encodeMap.put(entry.getKey(), entry.getValue().toString());
                }
            }

            encodeMap.put("sign", URLEncoder.encode(sign));
            return CommonUtil.getUrlParamsByMap(encodeMap);
        }
    }

    public static boolean checkSign(SortedMap<String, String> map) throws AlipayApiException {
        //sign与sign_type参数
        String sign = map.get("sign");
        //map.remove("sign");
        //map.remove("sign_type");
        String content = CommonUtil.getSortUrlParamsByMap(map);
        System.out.println("==========checksign======="+content);

        return AlipaySignature.rsaCheckV1(map, CustomizedPropertyConfigurer.getCtxProp("zfb_key"), "UTF-8", "RSA2");
        //return AlipaySignature.rsaCheckContent(content, sign, CustomizedPropertyConfigurer.getCtxProp("public_key"), "UTF-8");
    }

    public static void main(String[] args) {


        String subject ="";
        String body ="";
        try {
            subject = URLEncoder.encode("上海啦唔", "UTF-8");
            body = URLEncoder.encode("Pay", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(subject);
        System.out.println(body);

    }

}
