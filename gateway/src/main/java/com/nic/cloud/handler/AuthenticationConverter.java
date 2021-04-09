package com.nic.cloud.handler;

import cn.hutool.json.JSONUtil;
import com.nic.cloud.commons.base.login.LoginRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:13
 */
@Component
@Slf4j
public class AuthenticationConverter extends ServerFormLoginAuthenticationConverter {

	private String usernameParameter = "username";

	private String passwordParameter = "password";

	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		HttpHeaders headers = exchange.getRequest().getHeaders();
		String tenant = headers.getFirst("_tenant");
		String host = headers.getHost().getHostName();
		log.info("host:{}", host);
		Flux<DataBuffer> body = exchange.getRequest().getBody();
		return body.next().map(data -> {
			CharBuffer charBuffer = StandardCharsets.UTF_8.decode(data.asByteBuffer());
			LoginRequestDTO loginRequestDTO = JSONUtil.toBean(charBuffer.toString(), LoginRequestDTO.class);
			return new AuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword(), tenant, host);
		});
	}

}