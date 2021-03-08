package com.nic.commons.security;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/5 15:12
 */
@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		log.info("CustomUsernamePasswordAuthenticationFilter...");
		ObjectMapper objectMapper = new ObjectMapper();
		UsernamePasswordAuthenticationToken authRequest = null;
		try {
			InputStream is = request.getInputStream();
			LoginReq loginReq = objectMapper.readValue(is, LoginReq.class);
			log.info("loginReq:{}", JSONUtil.toJsonStr(loginReq));
			authRequest = new UsernamePasswordAuthenticationToken(loginReq.getLoginNo() + "@" + loginReq.getUsername(), loginReq.getPassword());
		} catch (Exception e) {
			log.error("usernamePasswordAuthentication exception", e);
			authRequest = new UsernamePasswordAuthenticationToken("", "");
		}
		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
