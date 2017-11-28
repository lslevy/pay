package com.love.pay.controller.wx;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.love.pay.controller.BaseController;
import com.love.pay.data.CommonConstant;
import com.love.pay.dto.Orders;
import com.love.pay.message.Message;
import com.love.pay.message.MessageID;
import com.love.pay.message.MessageState;
import com.love.pay.service.order.WXOrderService;
import com.love.pay.util.CommonUtil;
import com.love.pay.util.CustomizedPropertyConfigurer;
import com.love.pay.util.HttpRequestUtils;
import com.love.pay.util.HttpUtil;
import com.love.pay.util.pay.XmlUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by j_15 on 2016/12/28.
 *
 *
 */
@RestController
public class WxPayController extends BaseController {

    private static final Logger logger = Logger.getLogger(WxPayController.class);

    @Autowired
    private WXOrderService wxOrderService;
    /**
     * 微信提交订单
     * @param request
     * @return
     */
    @RequestMapping(CommonConstant.MSG_URI_PRE + MessageID.WX_COMMIT_ORDER)
    public Message commitOrder(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        Message msg = new Message(MessageID.WX_COMMIT_ORDER);
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
//            System.out.println(String.format("channel=%s ",orderJson.getString("channel")));
        }
        response.setCharacterEncoding("utf-8");
        orders.setMoney(orderJson.getFloat("money"));
        orders.setOrder_id(orderJson.getString("order_id"));
        orders.setMoney_str(orderJson.getString("money_str"));
        System.out.println("param order==========" + JSONObject.toJSONString(orders));
        try {
            String ip = CommonUtil.getIP(request);
            System.out.println("final order==========" + JSONObject.toJSONString(orders));

                Map map = wxOrderService.wxCommitOrder(orders, ip);

                System.out.println("====================wxCommitOrder=========result==========" + JSONObject.toJSONString(map));
                if(map.containsKey("msg")){
                    msg.setInfo((String)map.get("msg"));
                }
                msg.setD(map);
        } catch (Exception e) {
            logger.error("wxpay commit order err:"+e.getMessage());
            return msg.state(MessageState.WX_PAY_ERROR);
        }
        response.getOutputStream().write(msg.getD().toString().getBytes("utf-8"));
        response.getOutputStream().flush();
        response.getOutputStream().close();
        return msg;
    }

    /**
     * 微信查询订单
     * @return
     */
    @RequestMapping(CommonConstant.MSG_URI_PRE + MessageID.WX_QUERY_ORDER)
    public Message queryOrder(HttpServletRequest request, String out_trade_no){
        Message message = new Message(MessageID.WX_QUERY_ORDER);
        try {


            Map<String, String> map = wxOrderService.queryOrder(request, out_trade_no);


            if(!map.containsKey("return_code")){
                return message.state(MessageState.WX_QUERY_ERROR);
            }
            /**
             *  SUCCESS—支付成功
                REFUND—转入退款
                NOTPAY—未支付
                CLOSED—已关闭
                REVOKED—已撤销（刷卡支付）
                USERPAYING--用户支付中
                PAYERROR--支付失败(其他原因，如银行返回失败)
             */
            String code = map.get("return_code");
            String result = map.get("result_code");
            if ("SUCCESS".equals(code)&&"SUCCESS".equals(result)) {
                String state = map.get("trade_state");
                if ("SUCCESS".equals(state)) {
                    message.setD(4);
                } else if ("NOTPAY".equals(state)) {
                    message.setD(1);
                } else if ("CLOSED".equals(state)) {
                    message.setD(3);
                } else if ("PAYERROR".equals(state)) {
                    message.setD(1);
                }
            }
            System.err.println("======wx query order success===========");
        } catch (Exception e) {
            logger.error("wx query_order error======");
            e.printStackTrace();
            return message.state(MessageState.WX_QUERY_ERROR);
        }
        return message;
    }

    /**
     * 微信回调通知
     * @return
     */
    @RequestMapping(CommonConstant.MSG_URI_PRE + MessageID.WX_NOTIFY)
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            System.out.println("~~~~~~~~~~~~~~~~微信支付回调开始~~~~~~~~~");
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");
            System.out.println(result +"\r\n~~~~~~~~~~~~~~~微信支付回调结果~~~~~~~");

            Map<String, String> map = XmlUtils.doXMLParse(result);
            String code = map.get("return_code");
            String out_trade_no = map.get("out_trade_no");

            String transaction_id = map.get("transaction_id");//微信返回支付订单流水号

            if ("SUCCESS".equals(code)) {
                Long orderId = Long.parseLong(out_trade_no);


                //以下逻辑需要在数据库操作，初步考虑Http进入love_love项目进行数据验证以及订单状态修改
                //2017.03.03 ---levy
                //Order order = wxOrderService.checkOrder(orderType, type, orderId);
                Orders order = HttpUtil.getOrdersData(orderId.toString());
                if(null == order){
                    String xml = XmlUtils.toXml("FAIL", "订单不存在");
                    System.err.println("======wx notify order not exists===========");
                    writer.write(xml);
                    return;
                }

                if(order.getStatus() == 1){//已经处理过
                    String xml = XmlUtils.toXml("SUCCESS", "OK");
                    System.err.println("======wx notify success===========");
                    writer.write(xml);
                    return;
                }else{
                    //微信通知没那么强//加强通知处理
                    //先查询订单是否支付成功，直接修改状态放回成功状态，免得通知不成功时一直通知且得不到处理，影响用户余额的处理
                    //api查询微信订单支付状态
                    Message msg = this.queryOrder(request, out_trade_no);
                    if(msg.getC() == 1){
                        //核对金额信息
                        String str = order.getMoney_str();
                        Double d = Double.parseDouble(str)*100;
                        Integer price=d.intValue();
                        if(price != Integer.valueOf(map.get("total_fee"))){
                            String xml = XmlUtils.toXml("FAIL", "金额不匹配");
                            System.err.println("======wx notify amount not equals===========");
                            writer.write(xml);
                            return;
                        }
                        String resule_msg ="0";
                        //修改本地数据状态--订单状态和消费记录登记  优化什么的等第二个版本吧 Fuck
                        int state = (Integer)msg.getD();
                        if(state == 4){
                            //插入消费记录
//                            resule_msg = HttpRequestUtils.sendHttp("POST","http://love.91lovelove.cn/msg/705?channel_order_id="+transaction_id+"&state=1&debugAccountId=1&id="+order.getId()+"&money="+order.getMoney_str()+"&account_id="+order.getAccount_id(),"");
                            resule_msg = HttpRequestUtils.sendHttp("POST", CustomizedPropertyConfigurer.getCtxProp("orders_status_url")+"&id="+order.getId()+"&state=1&account_id="+order.getAccount_id()+"&channel_order_id="+transaction_id,"");
                            //resule_msg = HttpRequestUtils.sendPostObj("http://love.91lovelove.cn/msg/705?channel_order_id="+transaction_id+"&state=1&debugAccountId=1&id="+order.getId()+"&money="+order.getMoney_str()+"&account_id="+order.getAccount_id());
                            resule_msg = "1";
                        }

                        if("1".equals(resule_msg) ){
                            String xml = XmlUtils.toXml("SUCCESS", "OK");
                            writer.write(xml);
                            return;
                        }else{
//                            HttpRequestUtils.sendHttp("POST", "http://love.91lovelove.cn/msg/705?channel_order_id=" + transaction_id + "&state=-1&debugAccountId=1&id=" + order.getId() + "&money=" +order.getMoney_str()+ "&account_id=" + order.getAccount_id(), "");
                            HttpRequestUtils.sendHttp("POST", CustomizedPropertyConfigurer.getCtxProp("orders_status_url")+"&id="+order.getId()+"&state=-1&account_id="+order.getAccount_id()+"&channel_order_id="+transaction_id,"");
                            //HttpRequestUtils.sendPostObj( "http://love.91lovelove.cn/msg/705?channel_order_id=" + transaction_id + "&state=-1&debugAccountId=1&id=" + order.getId() + "&money=" +order.getMoney_str()+ "&account_id=" + order.getAccount_id());
                            String xml = XmlUtils.toXml("FAIL", "处理失败");
                            System.err.println("======wx notify error===========");
                            writer.write(xml);
                            return;
                        }

                    }else{
                        String xml = XmlUtils.toXml("FAIL", "订单不存在");
                        System.err.println("======wx notify order not exists===========");
                        writer.write(xml);
                        return;
                    }
                }

            }
            writer.close();
        } catch (Exception e) {
            logger.error("wx notify error:=======================");
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }



}
