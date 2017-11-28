package com.love.pay.dto;

import java.util.Date;

/**
 * Created by yuruyi on 2017/1/22.、
 * 用户充值订单
 */
public class Orders {
    /**编号*/
    private Long id;
    /**订单号*/
    private String order_id;
    /**订单状态*/
    private Integer status;
    /**账号id*/
    private Long account_id;
    /**充值金额*/
    private Float money;
    private String money_str;
    /**充值类型  充值类型  1充值钻石，2充值人民币，3兑换，4vip */
    private Integer type;
    /**充值渠道 1支付宝 2微信 3paypal*/
    private String channel;
    /**渠道订单号*/
    private String channel_order_id;
    /**渠道充值状态 成功1，失败-1*/
    private Integer channel_status;
    /**获得钻石*/
    private Integer dia;
    /**优惠赠送*/
    private Integer dia_benefit;
    /**常规赠送*/
    private Integer dia_common;
    /**本次合计获得钻石*/
    private Integer current_sum_dia;
    /**获得金额*/
    private Integer get_money;
    /**创建时间*/
    private Date create_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getMoney_str() {
        return money_str;
    }

    public void setMoney_str(String money_str) {
        this.money_str = money_str;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel_order_id() {
        return channel_order_id;
    }

    public void setChannel_order_id(String channel_order_id) {
        this.channel_order_id = channel_order_id;
    }

    public Integer getChannel_status() {
        return channel_status;
    }

    public void setChannel_status(Integer channel_status) {
        this.channel_status = channel_status;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getDia_benefit() {
        return dia_benefit;
    }

    public void setDia_benefit(Integer dia_benefit) {
        this.dia_benefit = dia_benefit;
    }

    public Integer getDia_common() {
        return dia_common;
    }

    public void setDia_common(Integer dia_common) {
        this.dia_common = dia_common;
    }

    public Integer getCurrent_sum_dia() {
        return current_sum_dia;
    }

    public void setCurrent_sum_dia(Integer current_sum_dia) {
        this.current_sum_dia = current_sum_dia;
    }

    public Integer getGet_money() {
        return get_money;
    }

    public void setGet_money(Integer get_money) {
        this.get_money = get_money;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
