package com.nic.cloud.controller;

import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.config.IMqttSender;
import com.nic.cloud.config.MqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/24 9:55
 */
@RestController
@Slf4j
public class MqttController {

	/**
	 * 注入发送MQTT的Bean
	 */
	@Resource
	private IMqttSender iMqttSender;
	@Autowired
	private MqttConfig mqttConfig;

	/**
	 * 发送MQTT消息
	 *
	 * @param message 消息内容
	 * @return 返回
	 */
	@ResponseBody
	@PostMapping(value = "/mqtt/{topic}")
	public ApiResult sendMqtt(@PathVariable String topic, @RequestBody String message) {
		log.info("topic:{}, message:{}", topic, message);
		mqttConfig.addTopics(topic);
		iMqttSender.sendToMqtt(topic, message);
		return ApiResult.ok();
	}
}
