package com.nic.cloud;

import com.nic.cloud.commons.base.IfcProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author james
 * @date 2021-02-03 10:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({
		IfcProperties.class
})
public class Gateway3Application {

	public static void main(String[] args) {
		SpringApplication.run(Gateway3Application.class, args);
	}

	@Bean
	@ConditionalOnMissingBean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
		return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
	}

}
