package com.nic.cloud.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/15 14:03
 */
public interface OutputSource {

	@Output("output1")
	MessageChannel output1();

	@Output("output2")
	MessageChannel output2();


}
