package com.love.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ruyi.yu on 2016/1/18.
 */
@Controller
public class AdminController extends BaseController {

	/**
	 * 登录
	 */
	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		return "test";
	}

	/**
	 * test
	 */
	@RequestMapping("/admin/del_xxx")
	public String del_xxx(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		Integer isLogin = (Integer) request.getAttribute("isLogin");
		System.out.println("isLogin" + isLogin);
		return "test";
	}

/*	*//**
	 * 登录
	 *//*
	@RequestMapping("/login_e")
	public String login_e(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		System.out.println("login_e");
		return null;
	}

	*//**
	 * 退出
	 *//*
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("admin");
		return "redirect:" + getBasePath(request) + "/login";
	}*/

}
