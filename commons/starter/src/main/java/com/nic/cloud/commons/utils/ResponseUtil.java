package com.nic.cloud.commons.utils;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.commons.base.utils.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/24 14:24
 */
public class ResponseUtil {

	public static void makeJsonResponse(HttpServletRequest request, HttpServletResponse response, ApiCode apiCode) throws IOException {
		response.setStatus(HttpStatus.HTTP_OK);
		response.setContentType("application/json; charset=UTF-8");
		ApiResult apiResult = ApiResult.result(apiCode);
		if (apiCode.getCode() != ApiCode.SUCCESS.getCode()) {
			String detailMessage = MessageUtil.buildDetailMessage(request.getMethod(), request.getRequestURI(), apiCode.getMessage());
			apiResult.detail(detailMessage);
		}
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(apiResult));
		out.flush();
		out.close();
	}

	public static void makeJsonResponse(HttpServletResponse response, Object data) throws IOException {
		response.setStatus(HttpStatus.HTTP_OK);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(ApiResult.result(data)));
		out.flush();
		out.close();
	}

}
