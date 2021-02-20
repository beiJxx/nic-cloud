package com.nic.cloud.commons.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/18 15:10
 */
@Configuration
public class RedicConfig {

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> redis = new RedisTemplate<>();
		redis.setConnectionFactory(redisConnectionFactory);

		// 设置redis的String/Value的默认序列化方式
		DefaultSerializer stringRedisSerializer = new DefaultSerializer();
		redis.setKeySerializer(stringRedisSerializer);
		redis.setValueSerializer(stringRedisSerializer);
		redis.setHashKeySerializer(stringRedisSerializer);
		redis.setHashValueSerializer(stringRedisSerializer);

		redis.afterPropertiesSet();
		return redis;
	}

}
