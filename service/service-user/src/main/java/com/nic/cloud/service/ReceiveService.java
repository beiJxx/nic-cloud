package com.nic.cloud.service;

import cn.hutool.json.JSONUtil;
import com.nic.cloud.config.InputSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/15 14:08
 */
@Service
@Slf4j
public class ReceiveService {

	private Integer CONSUME_COUNT = 1;

	@StreamListener(InputSource.INPUT1)
	public void receiveInput1(String receiveMsg) throws InterruptedException {
		log.info("input1 COUNT:{}, 接收到了消息：{}", CONSUME_COUNT, receiveMsg);
		Thread.sleep(2000L);
		log.info("input1 2s之后...");
		CONSUME_COUNT++;
		receiveMsg = receiveMsg + " " + CONSUME_COUNT;
		throw new RuntimeException("测试错误");
	}
/*
	@StreamListener(InputSource.INPUT2)
	public void receiveInput2(String receiveMsg) {
		log.info("input2 接收到了消息：{}", receiveMsg);
		throw new RuntimeException("测试错误2");
	}*/

	@ServiceActivator(inputChannel = "test-topic1.test-group1.errors")
	public void handleError(ErrorMessage errorMessage) {
		log.error("[handleError] : {}", JSONUtil.toJsonStr(errorMessage));
	}

/*
	@StreamListener(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
	public void globalHandleError(ErrorMessage errorMessage) {
		log.error("[handleGlobalError] : {}", JSONUtil.toJsonStr(errorMessage));
	}
*/

}
