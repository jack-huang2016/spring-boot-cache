/**
 * FileName: FastJsonRedisSerializer
 * Author:   huang.yj
 * Date:     2019/11/26 23:56
 * Description: 自定义的序列化类
 */
package com.sample.test.common.redisCache.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * 〈自定义的序列化类〉
 *
 * @author huang.yj
 * @create 2019/11/26
 * @since 0.0.1
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    static {
        //解决fastjson反序列化报错：com.alibaba.fastjson.JSONException: autoType is not support，添加白名单的作用，包名为想要反序列化类所在的包名
        ParserConfig.getGlobalInstance().addAccept("com.sample.test.common.entity");
    }

    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T)JSON.parseObject(str, clazz);
    }
}