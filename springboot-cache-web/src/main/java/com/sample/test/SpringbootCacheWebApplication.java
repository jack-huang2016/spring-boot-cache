package com.sample.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching  // 开启声明式缓存支持. 之后就可以使用 @Cacheable/@CachePut/@CacheEvict 注解缓存数据.
public class SpringbootCacheWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheWebApplication.class, args);
    }

}
