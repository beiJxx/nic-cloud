package com.nic.cloud.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:14
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Slf4j
public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

	private String tenant;

	private String host;

	public AuthenticationToken(Object principal, Object credentials, String tenant, String host) {
		super(principal, credentials);
		this.tenant = tenant;
		this.host = host;
	}

	public AuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public AuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}
}
