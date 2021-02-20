package com.nic.cloud.commons.base.api;

import lombok.AllArgsConstructor;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/4 15:42
 */
@AllArgsConstructor
public enum ApiCode {

	SUCCESS(200, "成功"),
	SERVER_ERROR(500, "系统内部错误"),
	FAIL(1000, "失败"),
	PARAM_LACK(1001, "参数缺失"),
	PARAM_ERROR(1002, "参数有误 [{0}]"),
	TOKEN_LACK(1003, "token传值有误"),
	USERNAME_PASSWORD_NOT_MATCH(1004, "用户名密码不匹配"),
	AUTHENTICATION_FAILED(401, "token无效，请重新登陆"),
	AUTHORIZATION_FAILED(403, "禁止访问"),
	NOT_FOUND(404, "路径有误");

	private int code;
	private String message;

	public static ApiCode getResultEnum(int code) {
		for (ApiCode type : ApiCode.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return FAIL;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
