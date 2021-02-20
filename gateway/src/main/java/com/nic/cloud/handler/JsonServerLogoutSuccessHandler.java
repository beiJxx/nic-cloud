package com.nic.cloud.handler;

import cn.hutool.json.JSONUtil;
import com.nic.cloud.commons.base.utils.MessageUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:11
 */
@Component
public class JsonServerLogoutSuccessHandler implements ServerLogoutSuccessHandler {
	@Override
	public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
		ServerHttpResponse response = exchange.getExchange().getResponse();
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		DataBuffer buffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(MessageUtil.buildSuccResultMap()).getBytes(Charset.defaultCharset()));
		return response.writeWith(Mono.just(buffer));
	}
}
