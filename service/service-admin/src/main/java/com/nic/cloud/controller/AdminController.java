package com.nic.cloud.controller;

import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.feign.UserFeignApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-03 16:12
 */
@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private UserFeignApi userFeignApi;

	@GetMapping("info")
	public ApiResult admin() {
		return userFeignApi.getUser();
	}


}
