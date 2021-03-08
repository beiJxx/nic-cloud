package com.nic.cloud.config;

import com.nic.cloud.security.TenantUserDetailsService;
import com.nic.cloud.security.handler.MyLoginSuccessHandler;
import com.nic.commons.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:10
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({
		CustomAccessDeniedHandler.class,
		CustomLogoutSuccessHandler.class,
		CustomLoginFailureHandler.class,
		JwtAuthenticationTokenFilter.class
})
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	@Autowired
	private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
	@Autowired
	private MyLoginSuccessHandler myLoginSuccessHandler;
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	@Autowired
	private CustomLoginFailureHandler customLoginFailureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService())
				.passwordEncoder(this.passwordEncoder());
	}

	@Bean
	@Override
	public TenantUserDetailsService userDetailsService() {
		return new TenantUserDetailsService();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
				.cors().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//				.and()
//				.formLogin()
//				.loginProcessingUrl("/web/login")
				.and()
				.logout()
				.logoutUrl("/web/tenant/logout")
				.logoutSuccessHandler(customLogoutSuccessHandler)
				.permitAll()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(customAccessDeniedHandler)
//				.authenticationEntryPoint(myAuthenticationEntryPoint)
				.and()
				.addFilterBefore(jwtAuthenticationTokenFilter, CustomUsernamePasswordAuthenticationFilter.class)
				.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.headers().cacheControl();
	}

	/**
	 * JSON登陆
	 */
	@Bean
	CustomUsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
		CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(myLoginSuccessHandler);
		filter.setAuthenticationFailureHandler(customLoginFailureHandler);
		filter.setFilterProcessesUrl("/web/tenant/login");
		//这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
		filter.setAuthenticationManager(authenticationManagerBean());
		return filter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}

}
