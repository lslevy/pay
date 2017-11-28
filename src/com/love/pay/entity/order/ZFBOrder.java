package com.love.pay.entity.order;

import java.util.Date;

/**
 * ZFBOrder 实体类
 * author  liusheng
 * Thu Jan 12 14:49:11 CST 2017
 */ 
public class ZFBOrder {
    /**编号*/
    private Long id;
    /**订单号*/
    private String order_id;
    /**订单状态*/
    private Integer status;
    /**账号id*/
    private Long account_id;
    /**充值金额*/
    private Integer money;
    /**渠道订单号*/
    private String channel_order_id;
    /**渠道充值状态 成功1，失败-1*/
    private Integer channel_status;
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

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
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


    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

