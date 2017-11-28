package com.love.pay.message;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;


/**
 * 消息类
 */
public class Message {

	Logger logger = Logger.getLogger(Message.class);

	/** 消息号 **/
	private int id;
	/** 状态码 code **/
	private int c = MessageState.SUCCESS;
	/** 描述 **/
	private String info;
	/** 参数 数据 **/
	//private Map<String, Object> d = new HashMap<>();
	private Object d = new JSONObject();

	@Deprecated
	public Message() {
	}

	public Message(int id) {
		this.id = id;
	}

	/**
	 * 设置参数
	 */
//	public Message setAttribute(String key, Object obj) {
//		this.d.put(key, obj);
//		return this;
//	}

	/**
	 * 获取value
	 */
	@SuppressWarnings("unchecked")
//	public <T> T getParameter(String key, Class<T> cls) {
//		Object obj = this.d.get(key);
//		if (obj == null) {
//			logger.warn(String.format("getParameter value is null , key=%s, cls=%s", key, cls.getSimpleName()));
//			return null;
//		}
//		// 检查数字
//		if (obj instanceof Number) {
//			// 泛型取long型时 实际为短整型
//			if ((cls.equals(Long.class) || cls.equals(long.class)) && obj instanceof Integer) {
//				Long l = ((Integer) obj).longValue();
//				return (T) l;
//			}
//			return (T) obj;
//		}
//		return (T) obj;
//	}

	/**
	 * 获取参数值
	 */
//	public Object getParameter(String key) {
//		return this.d.get(key);
//	}

	/**
	 * 清空
	 */
//	public void clear() {
//		//this.id = 0;
//		this.c = MessageState.SUCCESS;
//		this.d.clear();
//		this.info = null;
//	}

	/**
	 * 设置状态码
	 */
	public Message state(int state) {
		this.c = state;
		this.info = MessageState.getInfo(state);
		return this;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getD() {
		return d;
	}

	public void setD(Object d) {
		this.d = d;
	}
}
