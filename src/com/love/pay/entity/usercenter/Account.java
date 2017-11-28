package com.love.pay.entity.usercenter;

import java.util.Date;

/**
 * Created by yuruyi on 2016/12/13.
 */
public class Account {

    /**账号id*/
    private Long id;
    /**用户名*/
    private String username;
    /**昵称名*/
    private String nickname;
    /**真名*/
    private String realname;
    /**手机号*/
    private String mobile;
    /**自有平台密码*/
    private String password;
    /**所属平台名称*/
    private String plat_name;
    /**所属平台标识*/
    private String plat_token;
    /**ip*/
    private String ip;
    /**账号注册时间*/
    private Date register_time;
    /**性别1:女   2：男*/
    private int sex;
    /**年龄*/
    private int age;
    /**头像路径*/
    private String imgurl;
    /**微信号*/
    private String wx;
    /**职业*/
    private String professional;
    /**状态（0：无效，1：有效，2：冻结）*/
    private int state;
    /**地区*/
    private Integer area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlat_name() {
        return plat_name;
    }

    public void setPlat_name(String plat_name) {
        this.plat_name = plat_name;
    }

    public String getPlat_token() {
        return plat_token;
    }

    public void setPlat_token(String plat_token) {
        this.plat_token = plat_token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }
}
