package com.love.pay.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * Created by levy on 2017/6/1.
 */
public class RefundTest {

    public static void main(String[] args) {

        alipayRefundRequest("","2017090421001004080231286535",6.59);
    }
    /**
     *
     * @方法名称:alipayRefundRequest
     * @内容摘要: ＜支付宝退款请求＞
     * @param out_trade_no 订单支付时传入的商户订单号,不能和 trade_no同时为空。
     * @param trade_no 支付宝交易号，和商户订单号不能同时为空
     * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     */
    public static  String alipayRefundRequest(String out_trade_no,String trade_no,double refund_amount){
        String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXch+pD7DMmPdqBIfh+wDd4mpmCPpuDrXk18hYpLm41bJiCSTg3yYLyaWUJBRGVVBPvU5c+0QNit1DFRCb7w7QoSoggbkykWN6Y7oSc4zG/ngbbWa/c4XvaXQylL8GzlWbw2ETi882GjpetOC9GfMGtXP2g9D603X3aGgk1J5QqfllR1fTo/OOh39+SJXY/zg1aFt65e7fwWvDMPAbKH8ETZ/95qsz5pI/BYaxbt8wgR4lMyofysUi9EDPdmVzRNROUQ7LAE9JBixmI3w2Lnj8ZNo223GEpbHEw17giGGKVwlh1i4jAIf7Is0aMgdcjfqnt+C4jc1vIf2wYRmH4AI9AgMBAAECggEANv5UDNoLw1/QiAj5JtA0sl3jcQXVml1xI5ZPxBCc+EdxLTRQ1e9yK1mf7LllO10DetEtQYpkXd6NFizyCQ23/X5og8FXdMtiqjDIgqa++guxpZQGeYMDobpTXYKyE1CduaArAiQErIq9o2JfMYvYuhjt8RhQrKRY8+xP1P/4NSXUpyTfIw5s8DLxLrOu7O0bydgfolJFKLCvYASQhc1sSJLo44wvPvOCBiNj7c8RD/BgImN8Ir2RKyjFDlniTNbka8i40ogUd4ciz/Ogi850WfjIT/CQ5AgNpQLjZ8/p7g4G0oDaIT6GFHu1BF1UJoi3PUc/cxcK2dFprFbUwkcMqQKBgQDOa+QcFLYeGVrQOtNNgsCIXhNNrUS5ItWJ5iP6NTC2EP84P2R0oSKnBfbw6CjAGDf0LEsdAHhjCo5/j5i/HmRzazOUC5RRLG2ohktJLwyS5YEqPJ2nWlBMy5KEOVQPf2r1IehDGGlOJmo17TYyNyToA0HJ68qUZT1to8uFni02LwKBgQC70fp0heupMiMNhaCNU7oxhQ/kBHiTJKpkRrrmFM4s14SxH/qZa3jNMUvsBkyyDIuDfEyPmtbZMPe00teY4JQIIghTBMZMpL5E7ttbSZRllEjjXh+TFQCslVu00sHCTiI4sH8MLCrt+1d61yi0xLchKisJ+T/FN6EvXbWv3qRfUwKBgBWbXYlC8b080jCxt6SJZzXfAw5CcMsgRlHk8ScjojGsncp5eD5tD3+ENFELPBi/cDvy1vn2LqPPWxMnKMn4mHMsaX25qJDGocPeKstCTYKi215ccfQuk/E17eRBS/dDUyLNpc5AI+W2R6/VYLLo6sgObzh/ofjekAuF9avK3tipAoGBALoA1j9ljR/vpiUzC/BKVd/KvhT7lIlsS06peVh4jEJ+YUuktuIPPABB7MVe6798iwH5LD0fg9xuETJDh/4/knqHcaT92oB2unO79QLv2IXMl26MfUqFBzjjZYGrIvYRuPRZXkioyQ8L1fOydv6mvhGbGCp1bAEc+YTWEiOMo04/AoGBALR0s8xWCRu3pL/Is8Xm4g9HoXJwx9v3ojffLpXEHrl8JqzUKStQfbwuRUn5/w8KH1BJtctCY5xz4JNv3GZOaDX9pQIlxfIutJslR1NCz9nT4TArkcCm57EfegOPKklLxndYEmLAIQcqqE5RZCcmANOCaHFUqOVH2RUUDPN97sEN";
        String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl3IfqQ+wzJj3agSH4fsA3eJqZgj6bg615NfIWKS5uNWyYgkk4N8mC8mllCQURlVQT71OXPtEDYrdQxUQm+8O0KEqIIG5MpFjemO6EnOMxv54G21mv3OF72l0MpS/Bs5Vm8NhE4vPNho6XrTgvRnzBrVz9oPQ+tN192hoJNSeUKn5ZUdX06Pzjod/fkiV2P84NWhbeuXu38FrwzDwGyh/BE2f/earM+aSPwWGsW7fMIEeJTMqH8rFIvRAz3Zlc0TUTlEOywBPSQYsZiN8Ni54/GTaNttxhKWxxMNe4IhhilcJYdYuIwCH+yLNGjIHXI36p7fguI3NbyH9sGEZh+ACPQIDAQAB";
        String appid = "2017011205013331";
        String zfb_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo+jpqhy3mMjBRzkRzan8VQaqkJnO/o8r8dKvsdzDy+bDgOW4xOeuv3heAN4OsXZgo0nUR5tbnA5xOjTMeDYCbZb790TSbzcQfco4tmuRv8gS2jXapG1LXIFCkzfRRUogf3VGb+Iv79WojSEXkzr5trQR4i6iJz+jJJF4gfIZbGbR0EDhlFP4J1fcDUNzvJj0Ai4z5Oi8XyZzgz5dh2B5QzBn0dlsfqLeCzTkyG6Hgj2fYy8oU7cIeHsTJllc2o/EP22IK72qwScLtMO03praYF+puVfWSXs7NLwUTIyXk171YVngU5J/m9WzT/QrpqyAWUmHN1XQoj2TV8CUoD8JVQIDAQAB";
        // 发送请求
        String strResponse = null;
        try {
//            AlipayClient alipayClient = new DefaultAlipayClient
//                    ("https://openapi.alipay.com/gateway.do",appid,
//                            private_key,"json","GBK",public_key);


            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                    appid,private_key,"json","GBK",
                    zfb_key,"RSA2");


            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

            JSONObject biz = new JSONObject();
            biz.put("out_trade_no",out_trade_no);
            biz.put("refund_amount",refund_amount);
            biz.put("trade_no",trade_no);
            request.setBizContent(biz.toString());
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            strResponse=response.getCode();
            if ("10000".equals(response.getCode())) {
                strResponse="退款成功";
            }else {
                strResponse=response.getSubMsg();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;

    }
}
