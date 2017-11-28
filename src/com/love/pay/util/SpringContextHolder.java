package com.love.pay.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) {
		SpringContextHolder.context = context;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static <T> T getBean(String name) {
		return (T) context.getBean(name);
	}

}
