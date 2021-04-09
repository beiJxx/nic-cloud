package com.nic.cloud.message.response;

import com.nic.cloud.message.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/8 15:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryPersonAllResponse extends BaseMessage<QueryPersonAllResponse.Info> {
	@Override
	public String getOperator() {
		return "QueryPerson-Ack";
	}

	@Data
	public static class Info {
		private String faceluiceId;
		private String totalPersonNum;
		private String queryPersonNum;
		private String customId;
		private String result;
	}

}
