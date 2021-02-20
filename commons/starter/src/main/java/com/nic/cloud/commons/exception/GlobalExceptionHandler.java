package com.nic.cloud.commons.exception;

import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.commons.base.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/18 11:10
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 请求参数类型错误异常的捕获
	 *
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ApiResult badRequest(MethodArgumentNotValidException ex, HttpServletRequest request) {
		log.error("[GlobalExceptionHandler >>> MethodArgumentNotValidException]", ex);
		String message = ex.getBindingResult().getAllErrors()
				.stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(","));
		return response(ApiCode.PARAM_ERROR.getCode(),request,message,message);
	}

	/**
	 * 参数缺失
	 *
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = {HttpMessageConversionException.class})
	public ApiResult banParams(HttpMessageConversionException ex, HttpServletRequest request) {
		log.error("[GlobalExceptionHandler >>> HttpMessageConversionException]", ex);
		return response(ApiCode.PARAM_LACK.getCode(),request,ex.getMessage());
	}

	/**
	 * 自定义异常
	 *
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({BizException.class})
	public ApiResult openException(BizException ex, HttpServletRequest request) {
		log.error("[GlobalExceptionHandler >>> BizException]", ex);
		return response(ex.getCode(), request, ex.getMessage(),ex.getMessage());
	}

	/**
	 * 其他异常
	 *
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({Exception.class})
	public ApiResult exception(Exception ex, HttpServletRequest request) {
		log.error("[GlobalExceptionHandler >>> Exception]", ex);
		return response(ApiCode.SERVER_ERROR.getCode(), request, ex.getMessage());
	}

	private ApiResult response(int code, HttpServletRequest request, String detail, Object... message){
		return ApiResult
				.error(ApiCode.getResultEnum(code), message)
				.setDetail(MessageUtil.buildDetailMessage(request.getMethod(), request.getRequestURI(), detail));
	}


}
