package com.nic.cloud.config;

import com.nic.cloud.handler.*;
import com.nic.cloud.manager.AuthenticationManager;
import com.nic.cloud.manager.AuthorizeConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import java.util.LinkedList;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:10
 */
@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	private static final String[] AUTH_WHITELIST = new String[]{"/user/login", "/user/logout"};
	@Autowired
	private AuthenticationConverter authenticationConverter;
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	@Autowired
	private CustomServerAuthenticationEntryPoint serverAuthenticationEntryPoint;
	@Autowired
	private JsonServerAuthenticationSuccessHandler jsonServerAuthenticationSuccessHandler;
	@Autowired
	private JsonServerAuthenticationFailureHandler jsonServerAuthenticationFailureHandler;
	@Autowired
	private JsonServerLogoutSuccessHandler jsonServerLogoutSuccessHandler;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		SecurityWebFilterChain chain = http.formLogin()
				.loginPage("/user/login")
				// 登录成功handler
				.authenticationSuccessHandler(jsonServerAuthenticationSuccessHandler)
				// 登陆失败handler
				.authenticationFailureHandler(jsonServerAuthenticationFailureHandler)
				// 无访问权限handler
				.authenticationEntryPoint(serverAuthenticationEntryPoint)
				.and()
				.logout()
				// 登出成功handler
				.logoutSuccessHandler(jsonServerLogoutSuccessHandler)
				.and()
				.csrf().disable()
				.httpBasic().disable()
				.authorizeExchange()
				// 白名单放行
				.pathMatchers(AUTH_WHITELIST).permitAll()
				// 访问权限控制
				.anyExchange().access(authorizeConfigManager)
				.and().build();
		// 设置自定义登录参数转换器
		chain.getWebFilters()
				.filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
				.subscribe(webFilter -> {
					AuthenticationWebFilter filter = (AuthenticationWebFilter) webFilter;
					filter.setServerAuthenticationConverter(authenticationConverter);
				});
		return chain;
	}

	/**
	 * 注册用户信息验证管理器，可按需求添加多个按顺序执行
	 *
	 * @return
	 */
	@Bean
	ReactiveAuthenticationManager reactiveAuthenticationManager() {
		LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
		managers.add(authenticationManager);
		return new DelegatingReactiveAuthenticationManager(managers);
	}


	/**
	 * BCrypt密码编码
	 *
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
