package com.love.pay.dao;

import com.love.pay.util.RedisUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ruyi.yu on 2016/1/18.
 */
public class BaseDao {

	/**
	 * 获取List第一个
	 */
	public  <T> T getListFirst(List<T> list) {
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Resource
	protected JdbcTemplate jdbcTemplate_account;
	@Resource
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate_account;
	@Resource
	protected RedisUtil redisUtil_account;
	@Resource
	protected RedisUtil redisUtil_msg;


}
