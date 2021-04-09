package com.nic.cloud.service;

import com.nic.cloud.config.OutputSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/15 14:04
 */
@Service
@Slf4j
public class SenderService {

	@Autowired
	private OutputSource source;

	/**
	 * 发送字符串
	 *
	 * @param msg
	 */
	public void send(String msg) {
		Message message = MessageBuilder.withPayload(msg)
				.build();
		source.output1().send(message);
		log.info("send---> msg:{}", msg);
	}

	/**
	 * 发送带tag的字符串
	 *
	 * @param msg
	 * @param tag
	 */
	public void sendWithTags(String msg, String tag) {
		Message message = MessageBuilder.withPayload(msg)
				.setHeader(RocketMQHeaders.TAGS, tag)
				.build();
		source.output1().send(message);
		log.info("send---> msg:{},tag:{}", msg, tag);
	}

	/**
	 * 发送对象
	 *
	 * @param msg
	 * @param tag
	 * @param <T>
	 */
	public <T> void sendObject(T msg, String tag) {
		Message message = MessageBuilder.withPayload(msg)
				.setHeader(RocketMQHeaders.TAGS, tag)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build();
		source.output2().send(message);
	}


}
