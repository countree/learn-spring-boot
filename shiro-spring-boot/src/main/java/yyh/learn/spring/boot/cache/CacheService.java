package yyh.learn.spring.boot.cache;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 自定义cache 方便使用
 * 其它类操作缓存时注入这个cacheService
 *
 * @author yyh
 */
public interface CacheService<K, V> {
    /**
     * 添加缓存
     *
     * @param var1
     * @param var2
     */
    void set(K var1, V var2);

    /**
     * 添加缓存
     *
     * @param var1 k
     * @param var2 v
     * @param var3 过期时间
     * @param var5 时间单位
     */
    void set(K var1, V var2, long var3, TimeUnit var5);

    /**
     * 添加缓存
     *
     * @param var1       k
     * @param var2       v
     * @param expireTime 过期时间 单位/秒
     */
    void set(K var1, V var2, long expireTime);

    /**
     * 设置多个k v 对 使用map
     *
     * @param var1
     */
    void multiSet(Map<? extends K, ? extends V> var1);


    /**
     * k 获取缓存
     *
     * @param var1 k
     * @return
     */
    V get(Object var1);

    /**
     * 获取旧的缓存，设置新的缓存
     *
     * @param var1
     * @param var2
     * @return
     */
    V getAndSet(K var1, V var2);

}
