package com.nic.commons.security;

import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/24 17:09
 */
@Slf4j
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		log.info("CustomLoginFailureHandler...");
		ResponseUtil.makeJsonResponse(request, response, ApiCode.USERNAME_PASSWORD_NOT_MATCH);
	}
}
