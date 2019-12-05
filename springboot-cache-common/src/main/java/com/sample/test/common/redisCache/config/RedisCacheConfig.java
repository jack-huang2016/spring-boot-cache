/**
 * FileName: RedisConfig
 * Author:   huang.yj
 * Date:     2019/11/25 19:07
 * Description: redis配置类
 */
package com.sample.test.common.redisCache.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.sample.test.common.redisCache.serializer.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 〈redis配置类〉
 *
 * @author huang.yj
 * @create 2019/11/25
 * @since 0.0.1
 */
@Slf4j
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    // 缓存生存时间
    private Duration timeToLive = Duration.ofDays(1);

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 自定义CacheManager
     * @param factory 连接工厂
     * @return CacheManager
     * */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))  // 设置key值序列化
                       // 设置value值序列化
                       .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                        // 设置缓存的默认过期时间，使用Duration设置。
                        .entryTtl(timeToLive)
                        // 不缓存空值
                        .disableCachingNullValues();

        //解决fastjson反序列化报错：com.alibaba.fastjson.JSONException: autoType is not support，添加白名单的作用，包名为想要反序列化类所在的包名
        ParserConfig.getGlobalInstance().addAccept("com.sample.test.common.entity");
        // ParserConfig.getGlobalInstance().addAccept("com.baomidou"); 可以写多个该代码添加白名单

        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("my-redis-cache1", config);
        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));

        // 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .transactionAware()
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名
                .withInitialCacheConfigurations(configMap) // 再初始化相关的配置
                .build();

        log.debug("自定义RedisCacheManager加载完成");

        return cacheManager;
    }

    //缓存键自动生成器
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     *@描述 异常处理，当Redis发生异常时拦截，可打印日志等操作，但是程序正常走，不会抛异常
     *@参数  []
     *@返回值  org.springframework.cache.interceptor.CacheErrorHandler
     *@创建人  huang.yj
     *@创建时间  2019/12/5
     */
    /*@Bean
    @Override
    public CacheErrorHandler errorHandler() {
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key)    {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
        return cacheErrorHandler;
    }*/
}