package com.nic.cloud.controller.feign;

import cn.hutool.core.util.RandomUtil;
import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.feign.RoleFeignApi;
import com.nic.cloud.model.Role;
import com.nic.cloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/20 14:02
 */
@RestController
public class RoleFeignClient implements RoleFeignApi {

	@Autowired
	private RoleService roleService;

	@Override
	public ApiResult getRole() {
		return null;
	}

	@Override
	public ApiResult addRole() {
		Role role = new Role();
		role.setName(RandomUtil.randomString(5));
		int i = 1 / RandomUtil.randomInt(0, 1);
		roleService.save(role);
		return ApiResult.result(role);
	}
}
