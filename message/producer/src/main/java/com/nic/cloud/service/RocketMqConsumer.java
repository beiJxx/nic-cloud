package com.nic.cloud.service;

import com.alibaba.fastjson.JSONObject;
import com.nic.cloud.config.MqConstant;
import com.nic.cloud.config.RocketMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/11 16:00
 */
@Slf4j
@Service
public class RocketMqConsumer {
	@Autowired
	private RocketMqProperties mqProperties;


	@PostConstruct
	public void consumerTest() throws Exception {
		DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(MqConstant.ConsumeGroup.ES_USER_IMPORT);
		defaultMQPushConsumer.setNamesrvAddr(mqProperties.getNamesrvAddr());
		// * 代表不过滤
		defaultMQPushConsumer.subscribe(MqConstant.Topic.ES_USER_IMPORT, "*");
		defaultMQPushConsumer.setMaxReconsumeTimes(2);
		defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
			for (MessageExt msg : msgs) {
				try {
					byte[] body = msg.getBody();
					String tags = msg.getTags();
					String msgId = msg.getMsgId();
					log.info("reconsumeTimes:{},delayLevel:{},msgId:{},tags:{},body:{}", msg.getReconsumeTimes(), msg.getDelayTimeLevel(), msgId, tags,
							JSONObject.toJSONString(JSONObject.parse(body)));
					msg.setDelayTimeLevel(2);

					// 根据标签tag来决定什么操作
					if ("abc".equals(tags)) {
						return ConsumeConcurrentlyStatus.RECONSUME_LATER;
					}

				} catch (Exception e) {
					// 对次数进行冲正并且落库 todo 发送告警信息
					log.warn(String.format("即将导入ES库失败, 失败原因为{%s}", e.getMessage()), e);
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}

			}

			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

		});
		defaultMQPushConsumer.start();
		log.info(String.format("消费者{%s}启动了", MqConstant.Topic.ES_USER_IMPORT));
	}


}
