package com.nic.cloud.handler;

import cn.hutool.http.HttpStatus;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.utils.MessageUtil;
import com.nic.cloud.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.*;

import java.util.Map;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/18 10:52
 */
@Slf4j
public class JsonErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

	public JsonErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
	                                    ErrorProperties errorProperties, ApplicationContext applicationContext) {
		super(errorAttributes, resourceProperties, errorProperties, applicationContext);
	}

	/**
	 * 获取异常属性
	 */
	@Override
	protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		int code = ApiCode.SERVER_ERROR.getCode();
		Throwable error = super.getError(request);
		log.error("gateway exception", error);
		if (error instanceof BizException) {
			code = ((BizException) error).getCode();
		}
		return response(code, error, request);
	}

	/**
	 * 构建返回的JSON数据格式
	 *
	 * @param code    状态码
	 * @param error   异常信息
	 * @param request 请求
	 * @return
	 */
	public static Map<String, Object> response(int code, Throwable error, ServerRequest request) {
		return MessageUtil.buildResultMap(code, error.getMessage(), request.methodName(), request.uri().getPath());
	}

	/**
	 * 指定响应处理方法为JSON处理的方法
	 *
	 * @param errorAttributes
	 */
	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	/**
	 * 返回status 200
	 *
	 * @param errorAttributes
	 */
	@Override
	protected int getHttpStatus(Map<String, Object> errorAttributes) {
		return HttpStatus.HTTP_OK;
	}

}

