package com.nic.cloud.message.response;

import com.nic.cloud.message.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/8 15:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EditPersonResponse extends BaseMessage<EditPersonResponse.Info> {

	public static final String ACK = "EditPerson-Ack";

	@Override
	public String getOperator() {
		return ACK;
	}

	@Data
	public static class Info {
		private String customId;
		private String result;
	}
}
