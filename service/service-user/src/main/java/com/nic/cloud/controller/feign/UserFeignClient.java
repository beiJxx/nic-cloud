package com.nic.cloud.controller.feign;

import cn.hutool.core.util.RandomUtil;
import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.feign.UserFeignApi;
import com.nic.cloud.model.User;
import com.nic.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-03 15:46
 */
@RestController
public class UserFeignClient implements UserFeignApi {

	@Autowired
	private UserService userService;

	@Value("${server.port}")
	private int serverPort;

	@Override
	public ApiResult getUser() {
		return ApiResult.result("feign..." + serverPort);
	}

	@Override
	public ApiResult addUser() {
		User user = new User();
		user.setUsername(RandomUtil.randomString(5));
		user.setPassword(RandomUtil.randomString(20));
		userService.save(user);
		return ApiResult.result(user);
	}
}
