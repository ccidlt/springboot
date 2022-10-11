package com.ds.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * 加锁并设置过期时间
     *
     * @param lockName
     * @param lockExpire 过期时间 秒
     * @return
     */
    public boolean acquireLock(String lockName, int lockExpire) {
        if (redisTemplate.opsForValue().setIfAbsent(lockName, lockName)) {
            redisTemplate.expire(lockName, lockExpire, TimeUnit.SECONDS);
            return true;
        }
        if (redisTemplate.getExpire(lockName) == -1) {
            //保证锁一定设置过期时间
            redisTemplate.expire(lockName, lockExpire, TimeUnit.SECONDS);
        }
        return false;
    }

    /**
     * 加锁并设置过期时间
     *
     * @param lockName
     * @param lockExpire 过期时间 秒
     * @param acquireTimeout  获取锁超时时间  秒
     * @return
     */
    public boolean acquireLockWithTimeout(String lockName, int lockExpire, int acquireTimeout) {
        long end = System.currentTimeMillis() + acquireTimeout * 1000;
        while (System.currentTimeMillis() < end) {

            if (acquireLock(lockName, lockExpire)) {
                return true;
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    /**
     * 释放锁
     * @param lockName
     */
    public void releaseLock(String lockName){
        redisTemplate.delete(lockName);
    }

    /**
     * 加锁（自动重试）
     *
     * @param key
     * @param lockKeyType
     * @return
     */
    public boolean tryLock(String key, String lockKeyType) {
        boolean flag = false;
        try {
            key = lockKeyType + key;
            log.info("加锁请求数据，key：{}", key);

            long lockTimeout = 500 * 161;
            long sleepTimeout = 500;

            for (int i = 1; i <= 160; i++) {
                flag = this.lockOnce(key, lockTimeout);
                if (flag) {
                    log.info("{}加锁第{}次，成功", key, i);
                    break;
                } else {
//             log.info("{}加锁第{}次，失败", key, i);
                    Thread.sleep(sleepTimeout);
                }
            }
            if (flag) {
                log.info("{}加锁成功", key);
            } else {
                log.info("{}加锁失败", key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 加锁
     * @param key
     * @param lockKeyType
     * @return
     */
    public boolean lock(String key, String lockKeyType) {
        key = lockKeyType + key;
        log.info("加锁请求数据，key：{}", key);
        long lockTimeout = 1 * 1000;
        boolean flag = this.lockOnce(key, lockTimeout);
        if (flag) {
            log.info("{}加锁成功", key);
        } else {
            log.info("{}加锁失败", key);
        }
        return flag;
    }

    private boolean lockOnce(String key, long timeout) {
        String value = key + "_"+System.currentTimeMillis();
        boolean flag = redisTemplate.opsForValue().setIfAbsent(key, value,
                timeout, TimeUnit.MILLISECONDS);
        return flag;
    }

    /**
     * 解锁
     *
     * @param key
     * @param lockKeyType
     */
    public void releaseLock(String key, String lockKeyType) {
        key = lockKeyType + key;
        log.info("解锁请求数据，key：{}", key);
        try {
            redisTemplate.delete(key);
            log.info("{}解锁成功", key);
        } catch (Exception e) {
            log.error("{}解锁失败，{}", key, e);
        }
    }
}