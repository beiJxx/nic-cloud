package com.nic.commons.security;

import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/24 16:55
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
		log.info("CustomAccessDeniedHandler...");
		ResponseUtil.makeJsonResponse(httpServletRequest, httpServletResponse, ApiCode.AUTHORIZATION_FAILED);
	}
}
