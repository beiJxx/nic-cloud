package com.nic.cloud.feign;

import com.nic.cloud.commons.base.api.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-03 15:36
 */
@FeignClient(value = "nic-cloud-service-admin")
public interface RoleFeignApi {

	@GetMapping("/feign/role/info")
	ApiResult getRole();

	@PostMapping("/feign/role/info")
	ApiResult addRole();

}
