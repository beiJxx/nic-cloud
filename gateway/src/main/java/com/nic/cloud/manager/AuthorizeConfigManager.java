package com.nic.cloud.manager;

import cn.hutool.json.JSONUtil;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:24
 */
@Slf4j
@Component
public class AuthorizeConfigManager implements ReactiveAuthorizationManager<AuthorizationContext> {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
	                                         AuthorizationContext authorizationContext) {
		String token = authorizationContext.getExchange().getRequest().getHeaders().getFirst("ifc-token");
		log.info("AuthorizeConfigManager:{}", token);

		return authentication.map(auth -> {
			ServerWebExchange exchange = authorizationContext.getExchange();
			ServerHttpRequest request = exchange.getRequest();

			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			for (GrantedAuthority authority : authorities) {
				String authorityAuthority = authority.getAuthority();
				log.info("authorityAuthority:{}", authorityAuthority);
				String path = request.getURI().getPath();
				if (antPathMatcher.match(authorityAuthority, path)) {
					log.info(String.format("用户请求API校验通过，GrantedAuthority:{%s}  Path:{%s} ", authorityAuthority, path));
					return new AuthorizationDecision(true);
				}
			}
			return new AuthorizationDecision(false);
		}).defaultIfEmpty(new AuthorizationDecision(false));
	}

	@Override
	public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
		return check(authentication, object)
				.filter(AuthorizationDecision::isGranted)
				.switchIfEmpty(Mono.defer(() -> Mono.error(new AccessDeniedException(JSONUtil.toJsonStr(ApiResult.error(ApiCode.AUTHORIZATION_FAILED))))))
				.flatMap(d -> Mono.empty());
	}
}
