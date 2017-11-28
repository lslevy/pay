package com.love.pay.controller;

import com.love.pay.data.CommonConstant;
import com.love.pay.dto.Order;
import com.love.pay.message.MessageID;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by j_15 on 2016/12/28.
 */
@Controller
public class PayController extends BaseController {

    private static final Logger logger = Logger.getLogger(PayController.class);

    /**
     * 自有平台支付
     * @param order
     * @return
     */
    @RequestMapping(CommonConstant.MSG_URI_PRE + MessageID.PAY)
    public String pay(Order order, HttpServletRequest request, RedirectAttributes model) {
        int payType = order.getPay_type();
        model.addFlashAttribute(order);
        //订单类型
        if(1 == payType){//微信
            return "redirect:" + CommonConstant.MSG_URI_PRE + MessageID.WX_COMMIT_ORDER;
        }else if(2 == payType){//支付宝
            return "redirect:" + CommonConstant.MSG_URI_PRE + MessageID.ALI_PAY;
        }else if(3 == payType){//自有平台钱包
            return "redirect:" + CommonConstant.MSG_URI_PRE + MessageID.PLAT_PAY;
        }
        return null;
    }

}
