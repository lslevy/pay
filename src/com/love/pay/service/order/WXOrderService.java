package com.love.pay.service.order;

import com.love.pay.dao.order.WXOrderDao;
import com.love.pay.dto.Orders;
import com.love.pay.entity.xml.WxPayXml;
import com.love.pay.util.CommonUtil;
import com.love.pay.util.CustomizedPropertyConfigurer;
import com.love.pay.util.GenerateUtils;
import com.love.pay.util.HttpRequestUtils;
import com.love.pay.util.pay.WxSignUtils;
import com.love.pay.util.pay.XmlUtils;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/2/28.
 */
@Service("wXOrderService")
public class WXOrderService {


    @Autowired
    private WXOrderDao wxOrderDao;

    /**
     * 微信支付  返回微信预支付id
     * @return
     */
    public Map wxCommitOrder(Orders orders, String ip) throws Exception {
        String uuid = GenerateUtils.getRandomUUID();
        String appid = CustomizedPropertyConfigurer.getCtxProp("wxAppId");
        String macId = CustomizedPropertyConfigurer.getCtxProp("wxMchId");
        String notifyUrl = CustomizedPropertyConfigurer.getCtxProp("wxNotifyUrl");
        String orderId = orders.getOrder_id();
        String wxkey = CustomizedPropertyConfigurer.getCtxProp("wxKey");
        WxPayXml xml = new WxPayXml();

        xml.setAppid(appid);
        xml.setAttach("Test");


        xml.setBody("中文测试");
        //xml.setBody("Pay");


        xml.setMah_id(macId);
        xml.setNonce_str(uuid);
        xml.setNotify_url(notifyUrl);
        xml.setOut_trade_no(orderId);
        xml.setSpbill_create_ip(ip);
        //Int  订单总金额，单位为分，详见支付金额
        String str = orders.getMoney_str();
        Double d = Double.parseDouble(str)*100;
        int total_fee=d.intValue();
        xml.setTotal_fee(total_fee);
        xml.setTrade_type("APP");

        SortedMap signParams = WxSignUtils.beanToMap(xml);
        String sign = WxSignUtils.createSign("UTF-8",signParams,wxkey);
        signParams.put("sign",sign);
        String requesDtXml = WxSignUtils.getRequestXml(signParams);//生成Xml格式的字符串
        String url = CustomizedPropertyConfigurer.getCtxProp("wxCommitOrder");


        String result = HttpRequestUtils.sendHttp("post", url, requesDtXml);
        //String result = HttpRequestUtils.sendHttp("post", url, new String(requesDtXml.getBytes("UTF-8"), "ISO-8859-1"));


        System.out.println(result);
        //返回的result成功结果取出prepay_id：
        Map map = XmlUtils.doXMLParse(result);
        String return_code=(String) map.get("return_code");
        String prepay_id =null;
        if (return_code.contains("SUCCESS")){
            prepay_id=(String) map.get("prepay_id");//获取到prepay_id
        }else{
            Map error = new HashMap();
            error.put("prepay_id", prepay_id);
            error.put("msg", map.get("return_msg"));
            return map;
        }

        long currentTimeMillis = System.currentTimeMillis();//生成时间戳
        long second = currentTimeMillis / 1000L;//（转换成秒）
        String seconds = String.valueOf(second).substring(0, 10);//（截取前10位）
        String uuId = GenerateUtils.getRandomUUID();
        SortedMap<String, String> signParam = new TreeMap<String, String>();
        signParam.put("appid", appid);//app_id
        signParam.put("partnerid", macId);//微信商户账号
        signParam.put("prepayid", prepay_id);//预付订单id
        //signParam.put("packageName", "Sign=WXPay");//默认sign=WXPay
        signParam.put("package", "Sign=WXPay");//默认sign=WXPay
        signParam.put("noncestr", uuId);//自定义不重复的长度不长于32位
        signParam.put("timestamp",seconds);//北京时间时间戳

        System.out.println("第二次签名字符串:"+signParam);
        String signAgain = WxSignUtils.createSign("UTF-8", signParam, wxkey);//再次生成签名

        //signParam.put("sign", signAgain);
        signParam.put("sign", signAgain);

        StringBuffer buffer = new StringBuffer();
//        buffer.append("appid=").append(appid).append("&partnerid=")
//                    .append(macId).append("&prepayid=")
//                    .append(prepay_id).append("&package=Sign=WXPay")
//                    .append("&noncestr=").append(uuId).append("&timestamp=")
//                    .append(seconds).append("&sign=").append(signAgain);//拼接参数返回给移动端
//
//
        Map wx = new HashMap();
        //wx.put("prepay_id", prepay_id);
        //为了数据格式
        //signParam.put("prepay_id",prepay_id);
        wx.put("signParam", signParam);
        return wx;
    }

    /**
     * 微信api查询订单
     * @param request
     * @param out_trade_no
     */
    public Map<String, String> queryOrder(HttpServletRequest request, String out_trade_no) throws JDOMException, IOException {
        String uuid = GenerateUtils.getRandomUUID();
        String appid = CustomizedPropertyConfigurer.getCtxProp("wxAppId");
        String macId = CustomizedPropertyConfigurer.getCtxProp("wxMchId");
        String wxkey = CustomizedPropertyConfigurer.getCtxProp("wxKey");

        WxPayXml xml = new WxPayXml();
        xml.setAppid(appid);
        xml.setMah_id(macId);
        xml.setNonce_str(uuid);
        xml.setOut_trade_no(out_trade_no);
        xml.setSpbill_create_ip(CommonUtil.getIP(request));

        SortedMap signParams = WxSignUtils.beanToMap(xml);
        String sign = WxSignUtils.createSign("UTF-8",signParams,wxkey);
        signParams.put("sign",sign);
        String requestXml = WxSignUtils.getRequestXml(signParams);//生成Xml格式的字符串

        String url = CustomizedPropertyConfigurer.getCtxProp("wxQueryOrder");
        String result = HttpRequestUtils.sendHttp("post", url, requestXml);
        System.out.println(result);
        //返回的result成功结果取出prepay_id：
        Map<String, String> map = XmlUtils.doXMLParse(result);
        return map;
    }

}
