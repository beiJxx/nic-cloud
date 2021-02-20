package com.nic.cloud.handler;

import cn.hutool.json.JSONUtil;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:40
 */
@Component
@Slf4j
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
	@Override
	public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
		log.info("commence...");
		ServerHttpRequest request = serverWebExchange.getRequest();
		ServerHttpResponse response = serverWebExchange.getResponse();
		response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		response.setStatusCode(HttpStatus.OK);
		Map<String, Object> resultMap = MessageUtil.buildResultMap(ApiCode.AUTHORIZATION_FAILED.getCode(), e.getMessage(), request.getMethodValue(), request.getURI().getPath());
		DataBuffer buffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(resultMap).getBytes(Charset.defaultCharset()));
		return response.writeWith(Mono.just(buffer));
	}
}
