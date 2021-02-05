package com.nic.cloud.controller.dubbo;

import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.dubbo.UserDubboApi;
import lombok.Data;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-04 9:08
 */
@Data
@DubboService
public class UserDubboClient implements UserDubboApi {
	@Value("${server.port}")
	private int serverPort;

	@Override
	public ApiResult getUser() {
		return ApiResult.result("dubbo..." + serverPort);
	}
}
