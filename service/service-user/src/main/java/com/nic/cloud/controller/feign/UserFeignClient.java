package com.nic.cloud.controller.feign;

import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.feign.UserFeignApi;
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

	@Value("${server.port}")
	private int serverPort;

	@Override
	public ApiResult getUser() {
		return ApiResult.result("feign..." + serverPort);
	}
}
