package com.nic.cloud.filter;

import cn.hutool.core.util.ObjectUtil;
import com.nic.cloud.commons.base.Constants;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.utils.RedisUtil;
import com.nic.cloud.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * Description: 权限校验过滤器
 *
 * @author james
 * @date 2021/2/18 9:43
 */
@Slf4j
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

	private final PathMatcher pathMatcher = new AntPathMatcher();

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("权限校验 AuthorizeFilter...");
		String path = exchange.getRequest().getURI().getPath();
		String method = exchange.getRequest().getMethodValue();
		String authoritiy = "/" + method.toLowerCase() + ":" + path;
		log.info("method:{}, url:{}, authority:{}", method, path, authoritiy);
		if (pathMatcher.match("/*/login", path)) {
			log.info("login url ignore authorize...");
			return chain.filter(exchange);
		}
		HttpHeaders headers = exchange.getRequest().getHeaders();
		String token = headers.getFirst(Constants.HEADER_TOKEN);
		log.info("token:{}", token);
		if (ObjectUtil.isNull(token)) {
			throw new BizException(ApiCode.TOKEN_LACK);
		} else {
			String authorities = redisUtil.get(token);
			if (ObjectUtil.isEmpty(authorities)) {
				throw new BizException(ApiCode.AUTHENTICATION_FAILED);
			}
			boolean noneMatch = Arrays.stream(authorities.split(",")).noneMatch(k -> pathMatcher.match(k, authoritiy));
			if (noneMatch) {
				throw new BizException(ApiCode.AUTHORIZATION_FAILED);
			}

			String[] split = token.split("@");
			String username = split[1];
			exchange.getAttributes().put("username", username);
//			RequestContextHolder.currentRequestAttributes().setAttribute("username", username, 0);
			//认证成功则续签
			redisUtil.extendExpire(token, 30L);
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -100;
	}
}
