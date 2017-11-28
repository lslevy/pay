package com.love.pay.message;

/**
 * 消息号
 */
public class MessageID {
	/** 建立连接 **/
	public static final int HANDSHAKE = 1;
	/** 微信统一下单 **/
	public static final int WX_COMMIT_ORDER = 320;
	/** 微信查询订单 **/
	public static final int WX_QUERY_ORDER = 321;
	/** 微信回调通知 **/
	public static final int WX_NOTIFY = 322;
	/** 支付宝支付 **/
	public static final int ALI_PAY = 330;
	/** 支付宝退款 **/
	public static final int ALI_REFUND = 500;
	/** 支付宝查询订单 **/
	public static final int ALI_QUERY_ORDER = 331;
	/** 支付宝支付回调 **/
	public static final int ALI_PAY_NOTIFY = 332;
	/** 自有平台支付 **/
	public static final int PLAT_PAY = 340;
	/** 自有平台支付回调 **/
	public static final int PAYPAL_RET = 360;
	/** 支付整合 **/
	public static final int PAY = 350;
	/** 支付整合 **/
	public static final int PLAT_PAY_TEST = 400;
	/**退款*/
	public static final int REFUND = 500;
}
