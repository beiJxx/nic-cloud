package com.nic.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/11 15:53
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMqProperties {

	private String namesrvAddr;
	private Integer sendMsgTimeoutMillis;
	private Integer reconsumeTimes;
}
