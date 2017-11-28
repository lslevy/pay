package com.love.pay.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Order 实体类
 * author  lukylent
 * Thu Jan 12 11:00:10 CST 2017
 */ 
public class Order {
    //订单id
    private Long id;
    //订单类型（1：课程，2：教师，3：活动）
    private int order_type;
    //类型
  	private int type;
    //订购课程id 
  	private long course_id;
    //课程分类id 
  	private long category_id;
    //教师id
  	private long lecturer_id;
    //价格 
  	private BigDecimal price;
    //定制数量 
  	private int num;
    //是否使用悬赏券（1：是，0：否） 
  	private int is_coupon;
    //券id 
  	private String coupon_id;
    //优惠券总额度
    private BigDecimal coupon_fee;
    //是否使用余额（1：是，0：否）
    private int is_use_balance;
    //余额数量
    private BigDecimal balance;
    //客户姓名
  	private String customer;
    //上课人电话 
  	private String mobile;
    //付款方式（1：微信，2：支付宝） 
  	private int pay_type;
    //
    private long teach_mode_id;
    //状态(1：待付款，2：已取消，3：交易关闭，4：支付完成）
  	private int state;
    //创建人 
  	private long create_user;
    // 
  	private Date create_time;
    // 
  	private long update_user;
    //更新时间 
  	private Date update_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getLecturer_id() {
        return lecturer_id;
    }

    public void setLecturer_id(long lecturer_id) {
        this.lecturer_id = lecturer_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIs_coupon() {
        return is_coupon;
    }

    public void setIs_coupon(int is_coupon) {
        this.is_coupon = is_coupon;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public BigDecimal getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(BigDecimal coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public int getIs_use_balance() {
        return is_use_balance;
    }

    public void setIs_use_balance(int is_use_balance) {
        this.is_use_balance = is_use_balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public long getTeach_mode_id() {
        return teach_mode_id;
    }

    public void setTeach_mode_id(long teach_mode_id) {
        this.teach_mode_id = teach_mode_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCreate_user() {
        return create_user;
    }

    public void setCreate_user(long create_user) {
        this.create_user = create_user;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(long update_user) {
        this.update_user = update_user;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}

