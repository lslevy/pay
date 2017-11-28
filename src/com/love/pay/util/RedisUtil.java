package com.love.pay.util;

import org.apache.log4j.Logger;
import redis.clients.jedis.*;

public class RedisUtil {

    Logger logger = Logger.getLogger(RedisUtil.class);

    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 设置数据
     */
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("set", e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
    }

    /**
     * 获取数据
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("get", e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

    /**
     * 获取自增数据
     */
    public Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            logger.error("incr", e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

    /**
     * hset
     */
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error("hset", e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

    /**
     * hget
     */
    public String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("hset", e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

    /**
     * hdel
     */
    public Long hdel(String key, String... field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(key, field);
        } catch (Exception e) {
            logger.error("hset", e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

    /**
     * 不存在时更新
     */
    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            logger.error("hsetnx", e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return null;
    }

}
