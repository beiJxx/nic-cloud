package com.nic.cloud.message.request;

import com.nic.cloud.message.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/9 14:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OnlineConfirmRequest extends BaseMessage<OnlineConfirmRequest.Info> {
	@Override
	public String getOperator() {
		return "Online-Ack";
	}

	@Data
	public static class Info {
		private String result = "ok";
		private String facesluiceId;
		private String detail = "";
	}
}
