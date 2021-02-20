package com.nic.cloud.filter;

import cn.hutool.core.util.ObjectUtil;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/18 9:43
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("tokenFilter...");
		String path = exchange.getRequest().getURI().getPath();
		log.info("url:{}", path);

		if (antPathMatcher.match("/**/login", path)) {
			return chain.filter(exchange);
		}
		HttpHeaders headers = exchange.getRequest().getHeaders();
		String token = headers.getFirst("ifc-token");
		if (ObjectUtil.isNull(token)) {
			throw new BizException(ApiCode.TOKEN_LACK);
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -100;
	}
}
