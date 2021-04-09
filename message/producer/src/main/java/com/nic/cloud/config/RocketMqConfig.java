package com.nic.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/11 15:54
 */
@Configuration
@Slf4j
public class RocketMqConfig {

	@Autowired
	private RocketMqProperties rocketMqProperties;

	/**
	 * 初始化生产者
	 *
	 * @return
	 */
	@Bean
	public DefaultMQProducer defaultProducer() throws Exception {
		log.info("rocketMqProperties.getNamesrvAddr():{}", rocketMqProperties.getNamesrvAddr());
		// 实例化消息生产者Producer
		DefaultMQProducer producer = new DefaultMQProducer(MqConstant.ConsumeGroup.ES_USER_IMPORT);
		// 设置NameServer的地址
		producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
		// 设置发送消息超时时间
		producer.setSendMsgTimeout(rocketMqProperties.getSendMsgTimeoutMillis());
		// 设置重试次数
		producer.setRetryTimesWhenSendFailed(rocketMqProperties.getReconsumeTimes());
		producer.setVipChannelEnabled(false);
		// 启动Producer实例
		producer.start();
		return producer;
	}
}
