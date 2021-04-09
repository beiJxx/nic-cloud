package com.nic.cloud.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nic.cloud.message.BaseMessage;
import com.nic.cloud.message.response.EditPersonResponse;
import com.nic.cloud.service.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/24 14:24
 */
@Slf4j
@Component
public class TopicHandler {

	@Autowired
	private SenderService senderService;

	public void handler(Message<?> message) {
		log.info("[topicHandler] message:{}", JSONUtil.toJsonPrettyStr(message));
		JSONObject messageJson = JSONUtil.parseObj(message);
		JSONObject payload = messageJson.getJSONObject("payload");
		String operator = payload.getStr("operator");
		switch (operator) {
			case EditPersonResponse.ACK:
				EditPersonResponse editPersonResponse = JSONUtil.toBean(payload, EditPersonResponse.class);
				if (editPersonResponse.getInfo().getResult().equals(BaseMessage.OK)) {
					log.info(com.alibaba.fastjson.JSONObject.toJSONString(editPersonResponse));
					log.info("成功");
				}
				break;
			case "Online":
				String facesluiceId = payload.getJSONObject("info").getStr("facesluiceId");
				senderService.onlineConfirm(facesluiceId);
				break;
		}
	}
}
