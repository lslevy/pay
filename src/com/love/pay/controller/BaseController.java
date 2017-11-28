package com.love.pay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by ruyi.yu on 2016/1/18.
 */
public class BaseController {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static String getBasePath(HttpServletRequest request) {
        return new StringBuilder(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).toString();
    }

    /**
     * 组装跳转地址
     *
     * @param request
     * @param url
     * @return
     */
    public static String getRedirectUrl(HttpServletRequest request, String url) {
        return new StringBuilder("redirect:").append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(url).toString();
    }

    /**
     * 组装跳转地址
     *
     * @param request
     * @param url
     * @param msgState
     * @param msgContent
     * @return
     */
    public static String getRedirectUrl(HttpServletRequest request, RedirectAttributes attributes, String url, String msgState, String msgContent) {
        StringBuilder sb = new StringBuilder();
        sb.append("redirect:").append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(url);
        if (msgState != null && msgContent != null) {
            attributes.addFlashAttribute("msg_tip_state", msgState);
            attributes.addFlashAttribute("msg_tip", msgContent);
        }
        return sb.toString();
    }

    /**
     * 设置提示信息
     */
    public static Map<String, Object> setTipInfoMap(String msgState, String msgContent) {
        Map<String, Object> map = new HashMap<>();
        if (msgState != null && msgContent != null) {
            map.put("msg_tip_state", msgState);
            map.put("msg_tip", msgContent);
        }
        return map;
    }

}
