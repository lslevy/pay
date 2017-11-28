package com.love.pay.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息异常状态码
 */
public class MessageState {

	/** 系统出错 **/
	public static final int ERROR = -1;
	/** 成功 **/
	public static final int SUCCESS = 1;
    /** 参数错误 **/
    public static final int PARAM_ERROR = 104;
    /** 提交订单错误 **/
    public static final int ORDER_ERROR = 300;
    /** 支付方式与请求接口不符 **/
    public static final int PAY_TYPE_ERROR = 301;
    /** 平台支付错误 **/
    public static final int PLAT_PAY_ERROR = 302;
    /** 微信统一下单失败 **/
    public static final int WX_PAY_ERROR = 320;
    /** 微信查询订单失败 **/
    public static final int WX_QUERY_ERROR = 321;
    /** 签名错误 **/
    public static final int SIGN_ERROR = 330;
    /** 余额不符 **/
    public static final int BALANCE_IS_ERROR = 331;
    /** 余额不足 **/
    public static final int BALANCE_NOT_ENOUGH = 332;



    public static Map map = new HashMap();
    static {
        map.put(-1, "系统出错");
        map.put(1, "成功");
        map.put(104, "参数错误");
        map.put(300, "提交订单失败");
        map.put(301, "支付方式与请求接口不符");
        map.put(302, "平台支付错误");
        map.put(320, "微信统一下单失败");
        map.put(321, "微信查询订单失败");
        map.put(330, "签名错误");
        map.put(331, "余额不符");
        map.put(332, "余额不足");
    }
        /**
         * 获取错误码对应的文字信息
         */
        public static String getInfo(int code) {
            return (String) map.getOrDefault(code, map.get(code));

    }


}
