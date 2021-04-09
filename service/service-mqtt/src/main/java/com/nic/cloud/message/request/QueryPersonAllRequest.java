package com.nic.cloud.message.request;

import com.nic.cloud.message.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/8 14:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryPersonAllRequest extends BaseMessage<QueryPersonAllRequest.Info> {


	@Override
	public String getOperator() {
		return "QueryPerson";
	}


	@Data
	public static class Info {
		//设备号ID
		private String facesIuiceId;
	}


}
