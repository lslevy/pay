package com.love.pay.controller.refund;

import com.love.pay.controller.BaseController;
import com.love.pay.data.CommonConstant;
import com.love.pay.message.Message;
import com.love.pay.message.MessageID;
import com.love.pay.util.RefundTest;
import com.love.pay.util.wxrefund.RefundUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by levy
 */
@RestController
public class RefundController extends BaseController {

    private static final Logger logger = Logger.getLogger(RefundController.class);


    /**
     * 退款
     */
    @RequestMapping(CommonConstant.MSG_URI_PRE + MessageID.REFUND)
    public static Message refund(HttpServletRequest request) {
        Message msg = new Message(MessageID.REFUND);
        //支付宝第三方订单号
        String trade_no ="1234567";
        //微信第三方订单号
        String transaction_id ="7654321";
        Double refund_amount = 1.0;
        //支付宝
        RefundTest.alipayRefundRequest("",trade_no,refund_amount);
        //微信
        RefundUtil.wechatRefund1(transaction_id,"",refund_amount,refund_amount);
        return  msg;
    }



}
