package com.love.pay.util;

import com.love.pay.data.CommonConstant;
import org.apache.log4j.Logger;

/**
 * Created by yuruyi on 2016/12/12.
 */
public class LogUtil {

    /**获取默认日志*/
    public static Logger log(){
        return Logger.getLogger(CommonConstant.LOG_ACCOUNT);
    }

}
