package com.nic.cloud.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nic.cloud.config.MqConstant;
import com.nic.cloud.service.RocketMqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/11 15:28
 */
@RestController
public class TestController {


	@Autowired
	private RocketMqUtil rocketMqUtil;

	@PostMapping("/testSend/{tags}")
	public String testSend(@PathVariable("tags") String tags) {
		JSONObject obj = JSONUtil.createObj();
		obj.putOpt("aaaa", "111");
		obj.putOpt("bbbb", "222");
		rocketMqUtil.sendNormalDelayMessage(MqConstant.Topic.ES_USER_IMPORT, tags, obj, 0L);
//		rocketMqUtil.sendNormalMessage(MqConstant.Topic.ES_USER_IMPORT, tags, obj);
		return "success";
	}

}
