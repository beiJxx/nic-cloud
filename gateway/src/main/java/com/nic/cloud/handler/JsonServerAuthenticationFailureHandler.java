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
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:38
 */
@Component
@Slf4j
public class JsonServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
	@Override
	public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException e) {
		log.info("onAuthenticationFailure...", e);
		ServerHttpRequest request = webFilterExchange.getExchange().getRequest();
		ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
		response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		response.setStatusCode(HttpStatus.OK);
		Map<String, Object> resultMap = MessageUtil.buildResultMap(ApiCode.FAIL.getCode(), e.getMessage(), request.getMethodValue(), request.getURI().getPath());
		DataBuffer buffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(resultMap).getBytes(Charset.defaultCharset()));
		return response.writeWith(Mono.just(buffer));
	}
}
