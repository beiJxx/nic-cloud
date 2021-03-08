package com.nic.cloud.filter;

import com.nic.cloud.commons.base.Constants;
import com.nic.cloud.commons.base.IfcProperties;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.utils.JwtTokenUtil;
import com.nic.cloud.commons.base.utils.RedisUtil;
import com.nic.cloud.commons.exception.BizException;
import com.nic.cloud.util.GatewayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/5 16:08
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {

	@Autowired
	private IfcProperties ifcProperties;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		final ServerHttpRequest request = exchange.getRequest();
		HttpHeaders requestHeaders = request.getHeaders();
		String path = request.getURI().getPath();
		boolean ignore = GatewayUtil.ignoreUrl(path, ifcProperties.getTokenIgnoreUrls());

		if (ignore) {
			return chain.filter(exchange);
		}
		String ifctoken = requestHeaders.getFirst(Constants.HEADER_TOKEN);
//		log.info("ifc_token:{}", ifctoken);
		if (StringUtils.isEmpty(ifctoken)) {
			throw new BizException(ApiCode.TOKEN_LACK);
		}
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(ifctoken);
		if (StringUtils.isEmpty(usernameFromToken)) {
			throw new BizException(ApiCode.AUTHENTICATION_FAILED);
		}
		String serverToken = redisUtil.getToken(usernameFromToken);
		if (!StringUtils.equals(ifctoken, serverToken)) {
			throw new BizException(ApiCode.AUTHENTICATION_FAILED);
		}
		boolean extendExpire = redisUtil.extendExpire(usernameFromToken, ifcProperties.getTokenExpire());
		log.debug("[TokenFilter] {} extendExpire: {} min", usernameFromToken, extendExpire);
		return chain.filter(exchange.mutate()
				.request(
						exchange.getRequest().mutate()
								.headers(headers -> {
									headers.add(Constants.HEADER_USERNAME, usernameFromToken);
								}).build()
				).build());
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
