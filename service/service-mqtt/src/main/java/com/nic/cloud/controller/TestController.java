package com.nic.cloud.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.model.FaDeviceEmployee;
import com.nic.cloud.model.FaDeviceOperateLog;
import com.nic.cloud.service.FaDeviceEmployeeService;
import com.nic.cloud.service.FaDeviceOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/10 16:46
 */
@RestController
public class TestController {

	@Autowired
	private FaDeviceEmployeeService faDeviceEmployeeService;
	@Autowired
	private FaDeviceOperateLogService faDeviceOperateLogService;

	public static void main(String[] args) {

	}

	@GetMapping("/getInfos")
	public ApiResult getInfos() {

		List<FaDeviceEmployee> infos = faDeviceEmployeeService.getInfos();
		infos.forEach(k -> {
			String operator = "DelPerson";
			String messageId = "testDelete-" + System.currentTimeMillis() + "-" + RandomUtil.randomString(4);
			cn.hutool.json.JSONObject content = JSONUtil.createObj();
			content.putOpt("operator", operator);
			content.putOpt("messageId", messageId);
			cn.hutool.json.JSONObject info = JSONUtil.createObj();
			info.putOpt("customId", k.getEmployeeId());
			content.putOpt("info", info);
			String contentStr = JSONObject.toJSONString(content);

			FaDeviceOperateLog faDeviceOperateLog = new FaDeviceOperateLog();
			faDeviceOperateLog.setUserId(k.getEmployeeId());
			faDeviceOperateLog.setUsername(k.getEmployeeName());
			faDeviceOperateLog.setDeviceId(Integer.parseInt(k.getDeviceKey()));
//			faDeviceOperateLog.setDeviceId(1389286);
			faDeviceOperateLog.setUrl(k.getUrl());
			faDeviceOperateLog.setContent(contentStr);
			faDeviceOperateLog.setMessageId(messageId);
			faDeviceOperateLog.setTitle(operator);
//			faDeviceOperateLog.setUpdateTime(DateUtil.date());
			faDeviceOperateLogService.save(faDeviceOperateLog);
			System.out.println(JSONUtil.toJsonStr(faDeviceOperateLog));
		});
		return ApiResult.ok();
	}

}
