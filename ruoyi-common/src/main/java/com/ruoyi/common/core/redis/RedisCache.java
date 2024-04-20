package com.ruoyi.common.core.redis;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface RedisCache {

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    <T> void setCacheObject(final String key, final T value);

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit);

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    boolean expire(final String key, final long timeout);

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    boolean expire(final String key, final long timeout, final TimeUnit unit);

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    long getExpire(final String key);

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    Boolean hasKey(String key);

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    <T> T getCacheObject(final String key);

    /**
     * 删除单个对象
     *
     * @param key
     */
    boolean deleteObject(final String key);
    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    boolean deleteObject(final Collection<String> collection);

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    Collection<String> keys(final String pattern);
}
