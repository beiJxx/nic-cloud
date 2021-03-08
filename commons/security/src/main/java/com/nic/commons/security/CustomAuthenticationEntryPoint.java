package com.nic.commons.security;

import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/24 16:52
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
		log.info("CustomAuthenticationEntryPoint...");
		ResponseUtil.makeJsonResponse(httpServletRequest, httpServletResponse, ApiCode.AUTHENTICATION_FAILED);
	}
}
