package com.nic.cloud.message;

import lombok.Data;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/8 14:26
 */
@Data
public abstract class BaseMessage<T> {

	public static final String OK = "ok";
	public static final String FAIL = "fail";

	private String messageId;

	private T info;

	public abstract String getOperator();


}
