package com.nic.cloud.commons.base.api;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/4 15:41
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "响应结果")
public class ApiResult<T> implements Serializable {
	private static final long serialVersionUID = 5234853619048944775L;
	/**
	 * 响应码
	 */
	@ApiModelProperty(value = "响应码:200-请求处理成功")
	private int code;

	/**
	 * 响应消息
	 */
	@ApiModelProperty(value = "响应消息")
	private String message;
	/**
	 * 响应详情
	 */
	@ApiModelProperty(value = "响应详情")
	private String detail;

	/**
	 * 响应数据
	 */
	@ApiModelProperty(value = "响应数据")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	/**
	 * 响应时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time = LocalDateTime.now();

	public static ApiResult result(Object flag) {
		if (flag instanceof Boolean) {
			if ((Boolean) flag) {
				return ok();
			}
			return failed();
		} else if (flag instanceof ApiCode) {
			ApiCode apiCode = (ApiCode) flag;
			return result(apiCode.getCode(), apiCode.getMessage());
		} else {
			return ok().data(flag);
		}
	}

	public static ApiResult ok() {
		return new ApiResult().code(ApiCode.SUCCESS.getCode()).message(ApiCode.SUCCESS.getMessage());
	}

	public static ApiResult failed() {
		return new ApiResult().code(ApiCode.FAIL.getCode()).message(ApiCode.FAIL.getMessage());
	}

	public static ApiResult result(int code, String message) {
		return new ApiResult().setCode(code).setMessage(message);
	}

	public ApiResult data(T data) {
		this.data = data;
		return this;
	}

	public ApiResult message(String message) {
//		this.message = i18n(ApiCode.getResultEnum(this.code).getMessage(), message);
		this.message = message;
		return this;
	}

	public ApiResult code(int code) {
		this.code = code;
		return this;
	}

	public static ApiResult result(int code, String message, String detail) {
		return new ApiResult().setCode(code).setMessage(message).setDetail(detail);
	}

	public static String formatMessage(ApiCode apiCode, String... message) {
		return MessageFormat.format(apiCode.getMessage(), Arrays.stream(message).map(Object::toString).toArray());
	}

	public static void main(String[] args) {
		ApiResult error = error(ApiCode.FAIL);
		System.out.println(JSONUtil.toJsonStr(error));
	}

	public static ApiResult error(ApiCode apiCode, Object... format) {
		String message = MessageFormat.format(apiCode.getMessage(), Arrays.stream(format).map(Object::toString).toArray());
		return result(apiCode.getCode(), message);
	}

	@JsonIgnore
	public boolean isSuccess() {
		return ApiCode.SUCCESS.getCode() == this.getCode();
	}

	public ApiResult message(String messageFormat, Object... args) {
		if (StringUtils.isEmpty(messageFormat)) {
			messageFormat = ApiCode.getResultEnum(this.code).getMessage();
		}
		this.message = MessageFormat.format(messageFormat, Arrays.stream(args).map(Object::toString).toArray());
		return this;
	}

	public ApiResult message(Object... args) {
		this.message = MessageFormat.format(ApiCode.getResultEnum(this.code).getMessage(), Arrays.stream(args).map(Object::toString).toArray());
		return this;
	}

	@Override
	public String toString() {
		return "ApiResult{" +
				"code=" + code +
				", message='" + message + '\'' +
				", detail='" + detail + '\'' +
				", data=" + data +
				", time=" + time +
				'}';
	}
}

