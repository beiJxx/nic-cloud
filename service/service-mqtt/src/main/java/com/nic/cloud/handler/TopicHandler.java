package com.nic.cloud.handler;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
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

	public void handler(Message<?> message) {
		log.info("[topicHandler] message:{}", JSONUtil.toJsonPrettyStr(message));
	}
}
