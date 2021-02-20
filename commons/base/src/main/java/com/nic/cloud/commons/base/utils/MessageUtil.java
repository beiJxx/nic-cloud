package com.nic.cloud.commons.base.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.api.ApiResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 组装返回结果
 *
 * @author james
 * @date 2021/2/18 11:35
 */
public class MessageUtil {

	public static ApiResult buildResult(int code, String message, String methodName, String uri) {
		if (code == ApiCode.PARAM_ERROR.getCode()) {
			message = ApiResult.formatMessage(ApiCode.PARAM_ERROR, message);
		}
		return ApiResult.result(code, message, buildDetailMessage(methodName, uri, message));
	}

	public static String buildDetailMessage(String methodName, String uri, String message) {
		StringBuilder messageBuilder = new StringBuilder("Failed to handle request [");
		messageBuilder.append(methodName);
		messageBuilder.append(" ");
		messageBuilder.append(uri);
		messageBuilder.append("]");
		if (ObjectUtil.isNotNull(message)) {
			messageBuilder.append(": ");
			messageBuilder.append(message);
		}
		return messageBuilder.toString();
	}

	public static Map<String, Object> buildResultMap(int code, String message, String methodName, String uri) {
		HashMap<String, Object> response = new HashMap<>();
		response.put("code", code);
		response.put("message", message);
		response.put("detail", buildDetailMessage(methodName, uri, message));
		response.put("time", DateUtil.now());
		return response;
	}

	public static Map<String, Object> buildSuccResultMap(Object data) {
		HashMap<String, Object> response = new HashMap<>();
		response.put("code", ApiCode.SUCCESS.getCode());
		response.put("message", ApiCode.SUCCESS.getMessage());
		response.put("time", DateUtil.now());
		response.put("data", data);
		return response;
	}

	public static Map<String, Object> buildSuccResultMap() {
		HashMap<String, Object> response = new HashMap<>();
		response.put("code", ApiCode.SUCCESS.getCode());
		response.put("message", ApiCode.SUCCESS.getMessage());
		response.put("time", DateUtil.now());
		return response;
	}
}
