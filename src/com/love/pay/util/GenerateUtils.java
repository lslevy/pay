package com.love.pay.util;

import java.util.Random;
import java.util.UUID;

/**
 * Created by j_15 on 2016/11/30.
 */
public class GenerateUtils {
    /**
     * 获取随机数
     * @return
     */
    public static long getRandomNum(){
        Random randomNum = new Random();
        int random = randomNum.nextInt();
        return random & 0xffffffffL;
    }

    public static int getRandom() {
        return new Random().nextInt(999999)%900000+100000;
    }
    /**
     * 获取uuid
     * @return
     */
    public static String getRandomUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

}
