package com.love.pay.entity.xml;

/**
 * Created by Administrator on 2016/9/28.
 */

/**
 字段名	    变量名	        必填	类型	        示例值	                                    描述
 应用ID	    appid	        是	String(32)	wxd678efh567hg6787	                        微信开放平台审核通过的应用APPID
 商户号	    mch_id	        是	String(32)	1230000109	                                微信支付分配的商户号
 设备号	    device_info	    否	String(32)	013467007045764	                            终端设备号(门店号或收银设备ID)，默认请传"WEB"
 随机字符串	nonce_str	    是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	        随机字符串，不长于32位。推荐随机数生成算法
 签名	    sign	        是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	        签名，详见签名生成算法
 商品描述	    body	        是	String(128)	腾讯充值中心-QQ会员充值                         商品描述交易字段格式根据不同的应用场景按照以下格式： APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
 商品详情	    detail	        否	String(8192)Ipad mini  16G 白色	                        商品名称明细列表
 附加数据	    attach	        否	String(127)	深圳分店	                                    附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
 商户订单号	out_trade_no	是	String(32)	20150806125346	                            商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
 货币类型	    fee_type	    否	String(16)	CNY	                                        符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
 总金额	    total_fee	    是	Int	        888	                                        订单总金额，单位为分，详见支付金额
 终端IP	    spbill_create_ip是	String(16)	123.12.12.123	                            用户端实际ip
 交易起始时间	time_start	    否	String(14)	20091225091010	                            订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
 交易结束时间	time_expire	    否	String(14)	20091227091010                              订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010 注意：最短失效时间间隔必须大于5分钟
 商品标记	    goods_tag	    否	String(32)	WXG	                                        商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
 通知地址	    notify_url	    是	String(256)	http://www.weixin.qq.com/wxpay/pay.php	    接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
 交易类型	    trade_type	    是	String(16)	APP	                                        支付类型
 指定支付方式	limit_pay	    否	String(32)	no_credit	                                no_credit--指定不能使用信用卡支付
 */
public class WxPayXml {
    private String appid;
    private String mah_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String body;
    private String detail;
    private String attach;
    private String out_trade_no;
    private String out_refund_no;
    private String fee_type;
    private Integer total_fee;
    private Integer refund_fee;
    private String spbill_create_ip;
    private String time_start;
    private String time_expire;
    private String goods_tag;
    private String notify_url;
    private String trade_type;
    private String limit_pay;
    private String op_user_id;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMah_id() {
        return mah_id;
    }

    public void setMah_id(String mah_id) {
        this.mah_id = mah_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public Integer getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(Integer refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(String op_user_id) {
        this.op_user_id = op_user_id;
    }
}
