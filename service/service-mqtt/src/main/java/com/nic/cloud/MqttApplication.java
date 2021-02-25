package com.nic.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/24 9:48
 */
@SpringBootApplication
@EnableDiscoveryClient
@PropertySource(encoding = "UTF-8", value = {"classpath:config/mqtt.properties"})
public class MqttApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttApplication.class, args);
	}

}
