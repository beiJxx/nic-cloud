package com.nic.cloud.commons.exception;

import com.nic.cloud.commons.base.api.ApiCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/5 17:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {


	private static final long serialVersionUID = 4206404352822794161L;

	private int code = ApiCode.SERVER_ERROR.getCode();

	public BizException() {

	}

	public BizException(ApiCode apiCode) {
		super(apiCode.getMessage());
		this.code = apiCode.getCode();
	}

	public BizException(ApiCode apiCode, Object... args) {
		super(MessageFormat.format(apiCode.getMessage(), Arrays.stream(args).map(Object::toString).toArray()));
		this.code = apiCode.getCode();
	}

	public BizException(String msg) {
		super(msg);
	}

	public BizException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public BizException(int code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
	}

}
