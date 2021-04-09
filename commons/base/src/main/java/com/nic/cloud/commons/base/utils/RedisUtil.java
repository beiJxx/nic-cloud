package com.nic.cloud.commons.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Redis工具
 *
 * @author Zoctan
 * @date 2018/05/27
 */
@Component
public class RedisUtil {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * @param key
	 * @param value
	 * @param expire 有效期(分钟)
	 */
	public void set(String key, String value, Long expire) {
		stringRedisTemplate.opsForValue().set(formatKey(key), value, Duration.ofMinutes(expire));
	}

	private String formatKey(String key) {
		return key;
	}

	public String getToken(String username) {
		return get(username);
	}

	/**
	 * 获取值
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(formatKey(key));
	}

	/**
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(formatKey(key), value);
	}

	/**
	 * 删除缓存
	 *
	 * @param key
	 * @return
	 */
	public boolean remove(String key) {
		return stringRedisTemplate.delete(formatKey(key));
	}

	/**
	 * 延长有效期
	 *
	 * @param key
	 * @param expire 有效期(分钟)
	 */
	public boolean extendExpire(String key, Long expire) {
		return stringRedisTemplate.expire(formatKey(key), Duration.ofMinutes(expire));
//		return stringRedisTemplate.expire(formatKey(key), expire, TimeUnit.MINUTES);
	}

}
