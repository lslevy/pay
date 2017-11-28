package com.love.pay.controller.plat;

import com.love.pay.controller.BaseController;
import com.love.pay.data.CommonConstant;
import com.love.pay.dto.Orders;
import com.love.pay.message.MessageID;
import com.love.pay.util.HttpRequestUtils;
import com.love.pay.util.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by j_15 on 2016/12/28.
 */
@RestController
public class PlatPayController extends BaseController {

    private static final Logger logger = Logger.getLogger(PlatPayController.class);


    @RequestMapping(CommonConstant.MSG_URI_PRE + MessageID.PAYPAL_RET)
    public static String insert(HttpServletRequest httpRequest) {
        //定义变量和进行必要的初始化工作
        Enumeration parameterNames = null;
        String parameterName = null;
        String parameterValue = null;
        int count = 0;
        Vector[] params = null;
        Vector vParameterName = new Vector();
        Vector vParameterValue = new Vector();


        //判断paypal付款账户是否正确
        String business = httpRequest.getParameter("business");
        System.out.println("请求数据:"+httpRequest);
        System.out.println("商户帐号:"+business);
        //当前使用的是测试商户帐号
        if( !"2810300980-facilitator@qq.com".equals(business) ) {
            System.out.println("gu:Wrong receive paypal email:"+business);
            return null;
        }

        try {
            String orderId = httpRequest.getParameter("custom");//订单号
            String txn_id = httpRequest.getParameter("txn_id");//PayPal系统生成的唯一交易号。
            String orderId_2 = httpRequest.getParameter("cm");//订单号
            String txn_id_2 = httpRequest.getParameter("tx");//PayPal系统生成的唯一交易号。

            if(orderId==null||"".equals(orderId)){
                orderId = orderId_2;
            }
            if(txn_id==null||"".equals(txn_id)){
                txn_id = txn_id_2;
            }

            parameterNames = httpRequest.getParameterNames();
            boolean isPrint = false;
            while (parameterNames.hasMoreElements()) {//循环收取paypal发来的所有参数信息
                parameterName = (String) parameterNames.nextElement();
                parameterValue = httpRequest.getParameter(parameterName);
                if(parameterValue==null) parameterValue="";
                vParameterName.add(parameterName);
                vParameterValue.add(parameterValue);
                count++;
            }

            if(orderId!=null||!"".equals(orderId)) {
                //需要去外网测试
                Orders order = HttpUtil.getOrdersData(orderId.toString());
                if(order != null) {
                    //HttpRequestUtils.sendHttp("POST", "http://localhost:8080/msg/705?channel_order_id="+txn_id+"&state=1&debugAccountId=1&id=" + order.getId() + "&money=" + order.getMoney() + "&account_id=" + order.getAccount_id(), "");
                    HttpRequestUtils.sendHttp("POST", "http://love.91lovelove.cn/msg/705?channel_order_id="+txn_id+"&state=1&debugAccountId=1&id=" + order.getId() + "&money=" + order.getMoney() + "&account_id=" + order.getAccount_id(), "");
                }else{
                    //HttpRequestUtils.sendHttp("POST", "http://localhost:8080/msg/705?channel_order_id"+txn_id+"&state=-1&debugAccountId=1&id=" + order.getId() + "&money=" + order.getMoney() + "&account_id=" + order.getAccount_id(), "");
                    HttpRequestUtils.sendHttp("POST", "http://love.91lovelove.cn/msg/705?channel_order_id"+txn_id+"&state=-1&debugAccountId=1&id=" + order.getId() + "&money=" + order.getMoney() + "&account_id=" + order.getAccount_id(), "");
                }
            }
            //这里添加对收到信息的处理:一般是将这些信息存入数据库,然后对客户的订单进行处理.
            logger.getLogger("parameterName:"+parameterName);
            logger.getLogger("~~~~~~~~分割线~~~~~~~~~~~");
            logger.getLogger("parameterValue:"+parameterValue);
            return null;
        } catch (Exception e) {
            return e.toString();
        } finally {
            //
        }
    }



}
