package com.love.pay.controller.zfb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.love.pay.controller.BaseController;
import com.love.pay.data.CommonConstant;
import com.love.pay.dto.Orders;
import com.love.pay.message.Message;
import com.love.pay.message.MessageID;
import com.love.pay.message.MessageState;
import com.love.pay.service.order.ZFBOrderService;
import com.love.pay.util.CommonUtil;
import com.love.pay.util.CustomizedPropertyConfigurer;
import com.love.pay.util.HttpRequestUtils;
import com.love.pay.util.HttpUtil;
import com.love.pay.util.date.DateFormat;
import com.love.pay.util.date.DateUtils;
import com.love.pay.util.pay.AliSignUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 *
 * Created by j_15 on 2016/12/28.
 */
@RestController
public class AliPayController extends BaseController {

    private static final Logger logger = Logger.getLogger(AliPayController.class);
    @Autowired
    private ZFBOrderService zFBOrderService;

    /**
     * 支付宝支付
     * @return
     */
    @RequestMapping(CommonConstant.MSG_URI_PRE + MessageID.ALI_PAY)
    public Message alipay(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        Message msg = new Message(MessageID.ALI_PAY);
        Orders orders = new Orders();
        //等order订单接口确定后，再开启封印  已通
        request.setCharacterEncoding("UTF-8");
        BufferedReader br=new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String line="";
        String res="";
        while(null!=(line=br.readLine())){
            res+=line;
        }
        JSONObject orderJson = new JSONObject();
        JSONArray array=JSONArray.parseArray(res);
        for(int i=0;i<array.size();i++){
            orderJson=array.getJSONObject(i);
        }

        orders.setMoney(orderJson.getFloat("money"));
        orders.setOrder_id(orderJson.getString("order_id"));
        orders.setMoney_str(orderJson.getString("money_str"));
        try {
                    String date = DateUtils.DateToString(new Date(), DateFormat.YYYY_MM_DD_HH_MM_SS);
                    String sign = AliSignUtils.getSign(orders, date, true);
                    //String content = AliSignUtils.getSign(orders, date, true);
                    String url = AliSignUtils.getSign(orders, date, false);
                    Map map = new HashMap();
                    map.put("sign", sign);
                    map.put("url", url);
                    msg.setD(map);
                    //msg.state(MessageState.ORDER_ERROR);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getOutputStream().write(url.getBytes("UTF-8"));
            System.out.println("url:"+url);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (AlipayApiException e) {
            msg.state(MessageState.SIGN_ERROR);
            logger.error("alipay sign err:"+e.getMessage());
        }
        return msg;
    }
    /** 此处需等前端访问支付宝之后再开始修改逻辑---levy 2.28
     * 支付宝回调通知
     * @return
     */
    @RequestMapping(CommonConstant.MSG_URI_PRE + MessageID.ALI_PAY_NOTIFY)
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        System.out.println("~~~~~~~~~~~~~~~~支付宝支付回调开始~~~~~~~~~");
        outSteam.close();
        inStream.close();
        String back = new String(outSteam.toByteArray(), "utf-8");
        System.out.println(back +"\r\n~~~~~~~~~~~~~~~支付宝支付回调结果~~~~~~~");
        Map<String, Object> backMap = CommonUtil.getUrlParams(back);
        SortedMap<String, String> map = new TreeMap<>();
        for (Iterator<String> iter = backMap.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String value = (String)backMap.get(name);
//            if("sign".equals(name)){
//                map.put(name, value);
//            }else{
                map.put(name, URLDecoder.decode(value));

//            }
        }

        String subject = map.get("subject").toString();
        URLDecoder.decode(subject, "UTF-8");
        System.out.println("=============subject==========="+subject);
        boolean flag = false;
        try {
            flag = AliSignUtils.checkSign(map);
            System.out.println("=============alipay checksign success==========="+flag);
        } catch (AlipayApiException e) {
            System.out.println("=============alipay checksign error==========="+ e.getMessage());
            logger.error("alipay checksign error==========="+ e.getMessage());
        }
        if(flag){//验签成功
            Map result = checkInfo(map);
            System.out.println("===========local order======="+result);
            int i = (Integer)result.get("result");
            if(i == 1){//核对信息成功
                System.out.println("===============order check ok=============");

                //更改订单及余额消费信息
                Orders order = (Orders) result.get("order");
                System.out.println("===========order info======"+JSONObject.toJSONString(order));

                if("1".equals(order.getChannel())){//使用了第三方；否则都是自有平台支付
                    System.out.println("======alipay state update begin========");
                    //通知改变状态
                    String state = map.get("trade_status");
                    System.out.println("=======alipay notigy order state=="+ state);
                    /**
                     * 交易成功的状态都为1，是的都为-1
                     *
                     * WAIT_BUYER_PAY	交易创建，等待买家付款
                     * TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
                     * RADE_SUCCESS	交易支付成功
                     * TRADE_FINISHED	交易结束，不可退款
                     */
                    if("TRADE_SUCCESS".equals(state) || "TRADE_FINISHED".equals(state)){//完成交易//插入消费记录
                        //在my_data中对money;total_money进行金额增加操作
                        //更新订单状态
                        //HttpRequestUtils.sendHttp("POST","http://localhost:8080/msg/705?channel_order_id="+result.get("trade_no")+"&state=1&debugAccountId=1&id="+order.getId()+"&money="+order.getMoney()+"&account_id="+order.getAccount_id(),"");
                        //HttpRequestUtils.sendHttp("POST","http://love.91lovelove.cn/msg/705?channel_order_id="+result.get("trade_no")+"&state=1&debugAccountId=1&id="+order.getId()+"&money="+order.getMoney()+"&account_id="+order.getAccount_id(),"");
                        HttpRequestUtils.sendHttp("POST",CustomizedPropertyConfigurer.getCtxProp("orders_status_url")+"&id="+order.getId()+"&state=1&account_id="+order.getAccount_id()+"&channel_order_id="+result.get("trade_no"),"");
                        //HttpUtil.sendPostObj(result.get("trade_no").toString(),order,"http://love.91lovelove.cn/msg/705?debugAccountId=1");


                    }else if("TRADE_CLOSED".equals(state)){
                        //HttpRequestUtils.sendHttp("POST","http://localhost:8080/msg/705?state=-1&debugAccountId=1&id="+order.getId()+"&money="+order.getMoney()+"&account_id="+order.getAccount_id(),"");
//                        HttpRequestUtils.sendHttp("POST","http://love.91lovelove.cn/msg/705?state=-1&debugAccountId=1&id="+order.getId()+"&money="+order.getMoney()+"&account_id="+order.getAccount_id(),"");
                        HttpRequestUtils.sendHttp("POST",CustomizedPropertyConfigurer.getCtxProp("orders_status_url")+"&id="+order.getId()+"&state=-1&account_id="+order.getAccount_id()+"&channel_order_id="+result.get("trade_no"),"");
                        //HttpRequestUtils.sendPostK("http://love.91lovelove.cn/msg/705","state=-1&debugAccountId=1&id="+order.getId()+"&money="+order.getMoney()+"&account_id="+order.getAccount_id());
                    }
                }

            }else{
                System.out.println("===============order check fail=============");
                writer.write("failure");
            }
        }else{
            System.out.println("=============sign error======");
            writer.write("failure");
        }
        writer.close();
    }

    /***   此处逻辑已修改使用entity为Orders---levy 2.28
     * 验证商户信息和订单信息是否吻合
     * /**
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，
     *   则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，
     *   正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，
     *   只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     */
    private Map checkInfo(SortedMap<String, String> map) {
        Map result = new HashMap();
        String app_id = map.get("app_id");
        //本地订单ID
        String orderId = map.get("out_trade_no");
        //支付宝流水号
        String trade_no = map.get("trade_no");
        //金额
        Double total_amount = Double.parseDouble(map.get("total_amount"));

        System.out.println("orderId:"+orderId);

        //需要在love中获取数据
        Orders orders = HttpUtil.getOrdersData(orderId.toString());


        System.out.println("check order info=============="+app_id+"|||"+orderId+"|||"+total_amount);
        if(null == orders){//订单不存在
            //logger.info("alipay order not exists==================orderType="+orderType+"========type="+type+"============orderId="+orderId+"===");
            //System.out.println("alipay order not exists==================orderType="+orderType+"========type="+type+"============orderId="+orderId+"===");
            result.put("result", 0);
            return result;
        }
        //2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        String str = orders.getMoney_str();
        Double fee = Double.parseDouble(str);
        if(fee.doubleValue() != total_amount){
            logger.info("alipay order total_amount error==========");
            System.out.println("alipay order total_amount error==========");
            result.put("result", 0);
            return result;
        }
        //3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
        String pid = CustomizedPropertyConfigurer.getCtxProp("pid");
        String seller_id = map.get("seller_id");
        if(!pid.equals(seller_id)){
            logger.info("alipay order account pid/seller_id not equeal error==========");
            System.out.println("alipay order account pid/seller_id not equeal error==========");
            result.put("result", 0);
            return result;
        }
        //4、验证app_id是否为该商户本身
        String appid = CustomizedPropertyConfigurer.getCtxProp("appid");
        if(!appid.equals(app_id)){
            logger.info("alipay order account appid/app_id not equeal error==========");
            System.out.println("alipay order account appid/app_id not equeal error==========");
            result.put("result", 0);
            return result;
        }
        result.put("order", orders);
        result.put("trade_no", trade_no);
        result.put("result", 1);
        return result;
    }

    public static void main(String[] args) {



        //System.out.println(new BigDecimal(0.1).doubleValue());
    }
}
