package yyh.learn.spring.boot.config.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import yyh.learn.spring.boot.cache.CacheNameConstant;
import yyh.learn.spring.boot.cache.CacheNameExpireBean;
import yyh.learn.spring.boot.cache.CacheNameProperties;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在此处开启缓存注解，不需要在main方法上注解了
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    private static final Logger LOG = LoggerFactory.getLogger(RedisConfig.class);
    @Autowired
    CacheNameProperties cacheNamesProperties;

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 配置缓存管理器
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(@Qualifier("redisTemplate") RedisTemplate<?, ?> redisTemplate, RedisConnectionFactory factory) {
        Map<String, RedisTemplate> redisTemplateMap = new HashMap<String, RedisTemplate>();
        redisTemplateMap.put(CacheNameConstant.SHIRO_SESSION_CACHE_NAME, getJdkSerializeRedisTemplate(factory));

        RedisCacheManager cacheManager = new CustomRedisCacheManager(redisTemplate, redisTemplateMap);
        List<String> cacheNameList = new ArrayList<String>();
        Map<String, Long> expireMap = new HashMap<String, Long>();
//        List<CacheNameExpireBean> list = cacheNamesProperties.getConfiguredCacheNames();
//        for (CacheNameExpireBean cacheNameExpireBean : list) {
//            //这里的缓存名称其实就是redis中zset的key
//            cacheNameList.add(cacheNameExpireBean.getCacheName());
//            Long expireTime = cacheNameExpireBean.getExpireTime();
//            if (expireTime != null) {
//                //给单个缓存设置过期时间(其实还是对redis的key设置过期时间)单位/秒
//                expireMap.put(cacheNameExpireBean.getCacheName(), expireTime);
//            }
//        }
        cacheNameList.add(CacheNameConstant.DEFAULT_CACHE_NAME);
        cacheNameList.add(CacheNameConstant.SHIRO_AUTHORIZATION_CACHE_NAME);
        cacheNameList.add(CacheNameConstant.SHIRO_SESSION_CACHE_NAME);
        expireMap.put(CacheNameConstant.SHIRO_AUTHORIZATION_CACHE_NAME, 18000L);
        expireMap.put(CacheNameConstant.SHIRO_SESSION_CACHE_NAME, 36000L);
        cacheManager.setExpires(expireMap);
        cacheManager.setCacheNames(cacheNameList);
        LOG.info("初始化缓存名称:{}", cacheNameList);
        return cacheManager;
    }

    private RedisTemplate getJdkSerializeRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);


        template.setConnectionFactory(factory);
        //替换掉redis默认的序列化 使用json序列化
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 与RedisTemplate不同的是
     * redisTemplate在存储的时候key会自动加一个引号""
     * stringRedisTemplate.opsForValue().set("key-test-yhh", "第二次设置key-test-yyh");
     * redisTemplate.opsForValue().set("key-test-yyh", "第二次设置key-test-yyh");
     * <p>
     * 其它的操作类型都会在key上加上一个标识符如^keys表示redisTemplate的zset的key等
     * ，而stringRedisTemplate的zset的key自己写什么就是什么
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setConnectionFactory(factory);
        // value使用json序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * cache报异常时统一操作
     *
     * @return
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 用于捕获从Cache中进行CRUD时的异常的回调处理器。
        return new SimpleCacheErrorHandler();
    }
}
