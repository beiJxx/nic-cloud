package com.nic.cloud.commons.base.api;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	private String msg;

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
			return result(apiCode.getCode(), apiCode.getMsg());
		} else {
			return ok().data(flag);
		}
	}

	public static ApiResult ok() {
		return new ApiResult().code(ApiCode.SUCCESS.getCode()).msg(ApiCode.SUCCESS.getMsg());
	}

	public static ApiResult failed() {
		return new ApiResult().code(ApiCode.FAIL.getCode()).msg(ApiCode.FAIL.getMsg());
	}

	public static ApiResult result(int code, String msg) {
		return new ApiResult().setCode(code).setMsg(msg);
	}

	public ApiResult data(T data) {
		this.data = data;
		return this;
	}

	public ApiResult msg(String msg) {
//		this.msg = i18n(ApiCode.getResultEnum(this.code).getMsg(), msg);
		this.msg = msg;
		return this;
	}

	public ApiResult code(int code) {
		this.code = code;
		return this;
	}

	public static ApiResult error(ApiCode apiCode, Object... format) {
		String msg = MessageFormat.format(apiCode.getMsg(), Arrays.stream(format).map(Object::toString).toArray());
		return result(apiCode.getCode(), msg);
	}

	public ApiResult msg(String msgFormat, Object... args) {
		if (StringUtils.isEmpty(msgFormat)) {
			msgFormat = ApiCode.getResultEnum(this.code).getMsg();
		}
		this.msg = MessageFormat.format(msgFormat, Arrays.stream(args).map(Object::toString).toArray());
		return this;
	}

	public ApiResult msg(Object... args) {
		this.msg = MessageFormat.format(ApiCode.getResultEnum(this.code).getMsg(), Arrays.stream(args).map(Object::toString).toArray());
		return this;
	}

	@Override
	public String toString() {
		return "ApiResult{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				", time=" + time +
				'}';
	}

}

