package com.ruoyi.common.core.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
public class NoRedisCache implements RedisCache {

    private static final Map<String, Data> caches = new ConcurrentHashMap<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        caches.put(key, Data.of(value));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        caches.put(key, Data.of(value, timeout, timeUnit));
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        final Data data = getCache(key);
        if (data == null) {
            return false;
        }

        data.updateTime(timeout, unit);
        return true;
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        final Data cache = getCache(key);
        if (cache == null) {
            return 0;
        }
        return cache.timeout;
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return getCache(key) != null;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        return getCacheValue(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        caches.remove(key);
        return true;
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection<String> collection) {
        for (String key : collection) {
            caches.remove(key);
        }
        return true;
    }

//    /**
//     * 缓存List数据
//     *
//     * @param key 缓存的键值
//     * @param dataList 待缓存的List数据
//     * @return 缓存的对象
//     */
//    public <T> long setCacheList(final String key, final List<T> dataList)
//    {
//        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
//        return count == null ? 0 : count;
//    }
//
//    /**
//     * 获得缓存的list对象
//     *
//     * @param key 缓存的键值
//     * @return 缓存键值对应的数据
//     */
//    public <T> List<T> getCacheList(final String key)
//    {
//        return redisTemplate.opsForList().range(key, 0, -1);
//    }
//
//    /**
//     * 缓存Set
//     *
//     * @param key 缓存键值
//     * @param dataSet 缓存的数据
//     * @return 缓存数据的对象
//     */
//    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
//    {
//        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
//        Iterator<T> it = dataSet.iterator();
//        while (it.hasNext())
//        {
//            setOperation.add(it.next());
//        }
//        return setOperation;
//    }
//
//    /**
//     * 获得缓存的set
//     *
//     * @param key
//     * @return
//     */
//    public <T> Set<T> getCacheSet(final String key)
//    {
//        return redisTemplate.opsForSet().members(key);
//    }
//
//    /**
//     * 缓存Map
//     *
//     * @param key
//     * @param dataMap
//     */
//    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
//    {
//        if (dataMap != null) {
//            redisTemplate.opsForHash().putAll(key, dataMap);
//        }
//    }
//
//    /**
//     * 获得缓存的Map
//     *
//     * @param key
//     * @return
//     */
//    public <T> Map<String, T> getCacheMap(final String key)
//    {
//        return redisTemplate.opsForHash().entries(key);
//    }
//
//    /**
//     * 往Hash中存入数据
//     *
//     * @param key Redis键
//     * @param hKey Hash键
//     * @param value 值
//     */
//    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
//    {
//        redisTemplate.opsForHash().put(key, hKey, value);
//    }
//
//    /**
//     * 获取Hash中的数据
//     *
//     * @param key Redis键
//     * @param hKey Hash键
//     * @return Hash中的对象
//     */
//    public <T> T getCacheMapValue(final String key, final String hKey)
//    {
//        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
//        return opsForHash.get(key, hKey);
//    }
//
//    /**
//     * 获取多个Hash中的数据
//     *
//     * @param key Redis键
//     * @param hKeys Hash键集合
//     * @return Hash对象集合
//     */
//    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
//    {
//        return redisTemplate.opsForHash().multiGet(key, hKeys);
//    }
//
//    /**
//     * 删除Hash中的某条数据
//     *
//     * @param key Redis键
//     * @param hKey Hash键
//     * @return 是否成功
//     */
//    public boolean deleteCacheMapValue(final String key, final String hKey)
//    {
//        return redisTemplate.opsForHash().delete(key, hKey) > 0;
//    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        if (pattern == null || pattern.trim().isEmpty()) {
            return caches.keySet();
        }
        if (pattern.endsWith("*")) {
            String keyPrefix = pattern.substring(0, pattern.length() - 1);
            return caches.keySet()
                .stream()
                .filter(k -> k.startsWith(keyPrefix))
                .collect(Collectors.toList());
        }
        return caches.containsKey(pattern) ? Collections.singleton(pattern) : Collections.emptyList();
    }

    private static Data getCache(String key) {
        final Data data = caches.get(key);
        if (data == null) {
            return null;
        }
        if (data.timeEnd > System.currentTimeMillis()) {
            caches.remove(key);
            return null;
        }
        return data;
    }

    private static <T> T getCacheValue(String key) {
        final Data data = getCache(key);
        if (data == null) {
            return null;
        }
        return (T) data.value;
    }

    static class Data {
        private Object value;
        private long timeout;
        private long timeEnd;

        private void updateTime(long timeout , TimeUnit timeUnit) {
            if (timeout > 0 && timeUnit != null) {
                timeout = timeUnit.toSeconds(timeout);
                timeEnd = timeout * 1000;
            }
        }

        public static Data of(Object value) {
            final Data dt = new Data();
            dt.value = value;
            return dt;
        }

        public static Data of(Object value, long timeout , TimeUnit timeUnit) {
            final Data dt = of(value);
            dt.updateTime(timeout, timeUnit);
            return dt;
        }
    }

}
