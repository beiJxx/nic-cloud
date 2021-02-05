package com.nic.cloud.controller;

import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.dubbo.UserDubboApi;
import com.nic.cloud.feign.UserFeignApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-03 14:58
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserFeignApi userFeignApi;
	@DubboReference
	private UserDubboApi userDubboApi;

	@GetMapping("info")
	public ApiResult info() {
//		return userFeignApi.getUser();
		return userDubboApi.getUser();
	}
}
