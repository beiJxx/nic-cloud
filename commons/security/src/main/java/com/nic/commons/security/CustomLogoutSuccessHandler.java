package com.nic.commons.security;

import com.nic.cloud.commons.base.Constants;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.utils.JwtTokenUtil;
import com.nic.cloud.commons.base.utils.RedisUtil;
import com.nic.cloud.commons.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/24 18:25
 */
@Component
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		String token = request.getHeader(Constants.HEADER_TOKEN);
		if (StringUtils.isEmpty(token)) {
			ResponseUtil.makeJsonResponse(request, response, ApiCode.TOKEN_LACK);
			return;
		}
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		if (StringUtils.isEmpty(usernameFromToken)) {
			ResponseUtil.makeJsonResponse(request, response, ApiCode.TOKEN_LACK);
			return;
		}
		boolean remove = redisUtil.remove(usernameFromToken);
		log.info("[onLogoutSuccess] removeToken:{}", remove);
		ResponseUtil.makeJsonResponse(request, response, ApiCode.SUCCESS);
	}
}

