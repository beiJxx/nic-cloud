package com.nic.cloud.filter;

import cn.hutool.json.JSONUtil;
import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.dubbo.UserDubboApi;
import com.nic.cloud.feign.UserFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-03 14:46
 */
@Slf4j
@Component
public class RequestFilter implements GlobalFilter, Ordered {

	@DubboReference
	private UserDubboApi userDubboApi;
	@Autowired(required = true)
	private UserFeignApi userFeignApi;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("[requestFilter...]");
		ApiResult user = userDubboApi.getUser();
		log.info(JSONUtil.toJsonStr(user));
		ApiResult user1 = userFeignApi.getUser();
		log.info(JSONUtil.toJsonStr(user1));
		for (Map.Entry<String, Object> stringObjectEntry : exchange.getAttributes().entrySet()) {
			log.info("key:{},value:{}", stringObjectEntry.getKey(), stringObjectEntry.getValue());
		}
		InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
		log.info("address:{}", JSONUtil.toJsonStr(remoteAddress));

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
