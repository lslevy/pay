package com.love.pay.data;

/**
 * 平台枚举
 */
public enum PlatEnum {

	/** 聚教 **/
	EDU("edu"),
	/** 聚教 **/
	QQ("qq"),
	/** 微信 **/
	WX("wx"),
	/** 微博 **/
	WB("wb"),
    ;

	/** platName **/
	private String name;

	PlatEnum(String name) {
		this.name = name;
	}

	public static PlatEnum getByName(String name) {
		for (PlatEnum platEnum : PlatEnum.values()) {
			if (platEnum.getName().equals(name))
				return platEnum;
		}
		return null;
	}

	public String getName() {
		return name;
	}

    public String getAppId() {
        return null;
    }

	public String getAppCode() {
		return null;
	}

	public String getAppSecret() {
		return null;
	}

	public String getTokenUrl() {
		return null;
	}

}
