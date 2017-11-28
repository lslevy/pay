package com.love.pay.data;

/**
 * Created by yuruyi on 2016/12/12.
 */
public class CommonConstant {
    /**消息key*/
    public static final String MSG_KEY = "msg";
    /**account 日志*/
    public static final String LOG_ACCOUNT = "pay";
    /**所有消息开头的都从body体中获取*/
    public static final String MSG_URI_PRE = "/msg/";

    /**
     * redis key
     */
    public class RedisKey{
        /**账号自增id*/
        public static final String INCR_ACCOUNT = "incr_account";
        /**accountId=>token*/
        public static final String KEY_TOKEN_ID = "token:id";
        /**token=>accountId*/
        public static final String KEY_TOKEN_TOKEN = "token:token";
    }

    /**
     * live key
     */
    public class LiveKey{
        /**开启或关闭直播*/
        public static final String Live_Channel_SetStatus = "Live_Channel_SetStatus";

    }

}
