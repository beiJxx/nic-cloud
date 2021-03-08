package com.nic.cloud.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.commons.base.login.LoginRequestDTO;
import com.nic.cloud.commons.base.utils.RedisUtil;
import com.nic.cloud.feign.UserFeignApi;
import com.nic.cloud.pojo.dto.UserDTO;
import com.nic.cloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-03 14:58
 */
@RestController
@RequestMapping("web/tenant")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserFeignApi userFeignApi;


	@Autowired
	private RedisUtil redisUtil;

	public static void main(String[] args) {
		byte b = Byte.parseByte("11");
		System.out.println(b);
	}

	@GetMapping("info1")
	@PreAuthorize("hasAuthority('info1')")
	public ApiResult info1() {
		return userFeignApi.getUser();
	}

	@GetMapping("info2")
	@PreAuthorize("hasAuthority('info2')")
	public ApiResult info2() {
		return userFeignApi.getUser();
	}

	@GetMapping("info3")
	@PreAuthorize("hasAuthority('info3')")
	public ApiResult info3() {
		return userFeignApi.getUser();
	}

	@GetMapping("info3/{id}")
	public ApiResult info3(@PathVariable("id") Integer id) {
		return userFeignApi.getUser();
	}

	@GetMapping("info3/{id}/info")
	public ApiResult info3Info(@PathVariable("id") Integer id) {
		return userFeignApi.getUser();
	}

	@PostMapping("")
	public ApiResult addUserAndRole(@RequestBody UserDTO userDTO) {
		return ApiResult.result(userService.addUserAndRole(userDTO));
	}

	@PostMapping("login")
	public ApiResult login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
		log.info("loginRequest:{}", JSONUtil.toJsonStr(loginRequestDTO));
		String oriToken = redisUtil.getToken(loginRequestDTO.getUsername());
		if (ObjectUtil.isNotEmpty(oriToken)) {
			redisUtil.remove(oriToken);
		}
//		String newToken = SecureUtil.md5("1qaz!QAZ2wsxWSX@" + loginRequestDTO.getUsername() + "@" + DateUtil.thisMillisecond());
		String newToken = "1qaz!QAZ2wsxWSX@" + loginRequestDTO.getUsername() + "@" + DateUtil.thisMillisecond();
		String autorities = "/get:/user/info1,/get:/user/info2,/get:/user/info3/*,/get:/admin/info,/post:/user";
		redisUtil.set(newToken, autorities, 30L);
		redisUtil.set(loginRequestDTO.getUsername(), newToken, 30L);
		JSONObject obj = JSONUtil.createObj();
		obj.putOpt("token", newToken);

		return ApiResult.result(obj);
	}
}
