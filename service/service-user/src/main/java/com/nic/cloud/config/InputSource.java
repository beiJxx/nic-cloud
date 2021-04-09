package com.nic.cloud.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/15 14:07
 */
public interface InputSource {

	String INPUT1 = "input1";
//	String INPUT2 = "input2";

	@Input(INPUT1)
	SubscribableChannel input1();

//	@Input(INPUT2)
//	SubscribableChannel input2();

}
