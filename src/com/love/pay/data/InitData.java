package com.love.pay.data;

import java.util.*;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by ruyi.yu on 2016/1/25.
 */
@Component("initData")
public class InitData implements InitializingBean {

	// 通用url 只需要登录就可以访问
	public static final List<String> COMMON_URL_LIST = new ArrayList<>(Arrays.asList(new String[] { "/a/main", "/a/welcome", "/a/game/get_server_list", "/a/no_right", "/a/heartbeat" }));

	@Override
	public void afterPropertiesSet() throws Exception {
	}







}
