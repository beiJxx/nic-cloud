package com.nic.cloud.feign;

import com.nic.cloud.commons.base.api.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-03 15:36
 */
@FeignClient(value = "nic-cloud-service-user")
public interface UserFeignApi {

	@GetMapping("/feign/user/info")
	ApiResult getUser();

}
