package com.love.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

public class EncodingDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 6162405022799073217L;

	/**防止乱码*/
	private static final String ENCODING = "UTF-8";
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(ENCODING);
/*		if (request.getRequestURI().startsWith(CommonConstant.MSG_URI_PRE)){
			try {
				Message message = JSON.parseObject(request.getInputStream(), Message.class);
				request.setAttribute(CommonConstant.MSG_KEY, message);
			} catch (Exception e) {
				LogUtil.log().error("[EncodingDispatcherServlet parse json]", e);
			}
		}*/

		super.doService(request, response);
	}

}