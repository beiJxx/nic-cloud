package com.nic.cloud.security.handler;

import cn.hutool.json.JSONUtil;
import com.nic.cloud.commons.base.IfcProperties;
import com.nic.cloud.commons.base.login.AbstractUserDetails;
import com.nic.cloud.commons.base.utils.JwtTokenUtil;
import com.nic.cloud.commons.base.utils.RedisUtil;
import com.nic.cloud.commons.utils.ResponseUtil;
import com.nic.cloud.security.LoginRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/24 17:08
 */
@Slf4j
@Component
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IfcProperties ifcProperties;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		log.info("MyLoginSuccessHandler...");
		AbstractUserDetails userDetails = (AbstractUserDetails) authentication.getPrincipal();
		log.info("userDetails:{}", JSONUtil.toJsonStr(userDetails));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		//生成token
		String token = JwtTokenUtil.generateToken(userDetails);
		LoginRes loginRes = new LoginRes();
		loginRes.setUserId(String.valueOf(userDetails.getUserId()));
		loginRes.setUsername(userDetails.getUsername());
		loginRes.setToken(token);
//		List<Menu> menusByUserId = menuService.getMenusByUserId(userDetails.getUserId());
//		loginRes.setMenuList(BeanConvertUtils.convertToList(menusByUserId, LoginRes.Menu.class));
//		Admin byId = adminService.getById(userDetails.getUserId());
//		if (null == byId) {
//			ResponseUtil.makeJsonResponse(response, HttpStatus.OK.value(), ApiResult.error(ApiCode.USERNAME_PASSWORD_NOT_MATCH));
//			return;
//		}
		redisUtil.set(userDetails.getUsername(), token, ifcProperties.getTokenExpire());
		ResponseUtil.makeJsonResponse(response, loginRes);
	}
}
