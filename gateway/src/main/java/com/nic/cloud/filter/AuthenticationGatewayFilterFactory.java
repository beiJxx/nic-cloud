package com.nic.cloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/4 17:47
 */
@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
	@Override
	public GatewayFilter apply(Object config) {

		return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
			List<String> gmAccountInfoJsons = exchange.getResponse().getHeaders().get("AccountInfo");
			exchange.getResponse().getHeaders().remove("AccountInfo");//移除包头中的用户信息不需要返回给客户端
			if (gmAccountInfoJsons != null && gmAccountInfoJsons.size() > 0) {
				String gmAccountInfoJson = gmAccountInfoJsons.get(0);//获取header中的用户信息
				//将信息放在session中，在后面认证的请求中使用
				exchange.getSession().block().getAttributes().put("AccountInfo", gmAccountInfoJson);
			}
			log.debug("登陆返回信息:{}", gmAccountInfoJsons);
		}));
	}
}
