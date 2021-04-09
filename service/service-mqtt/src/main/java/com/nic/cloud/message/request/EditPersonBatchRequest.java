package com.nic.cloud.message.request;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.nic.cloud.message.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/9 9:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EditPersonBatchRequest extends BaseMessage<List<EditPersonBatchRequest.Info>> {

	@JSONField(name = "DataBegin")
	private String dataBegin = "BeginFlag";
	@JSONField(name = "DataEnd")
	private String dataEnd = "EndFlag";

	@JSONField(name = "PersonNum")
	private Integer personNum;
	private List<EditPersonBatchRequest.Info> info;

	public static void main(String[] args) {
		EditPersonBatchRequest editPersonBatchRequest = new EditPersonBatchRequest();
		editPersonBatchRequest.setMessageId("123");

		List<EditPersonBatchRequest.Info> infoss = new ArrayList<>();
		infoss.add(new EditPersonBatchRequest.Info() {{
			setCustomId("123");
		}});
		infoss.add(new EditPersonBatchRequest.Info() {{
			setCustomId("222");
		}});
		editPersonBatchRequest.setInfo(infoss);
		System.out.println(JSONObject.toJSONString(editPersonBatchRequest));

	}

	@Override
	public String getOperator() {
		return "AddPersons";
	}

	@Data
	public static class Info {
		//白名单
		public static final int PERSON_TYPE_WHITE = 0;
		//黑名单
		public static final int PERSON_TYPE_BLACK = 1;

		//永久名单
		public static final int TEMP_CARD_TYPE_FOREVER = 0;
		//临时名单（时间段有效）
		public static final int TEMP_CARD_TYPE_TEMP1 = 1;
		//临时名单（每天同一时间段有效）
		public static final int TEMP_CARD_TYPE_TEMP2 = 2;
		//临时名单（次数有效）
		public static final int TEMP_CARD_TYPE_TEMP3 = 3;

		private String customId;
		private String name;
		private Integer personType;
		private Integer tempCardType;
		private String picURI;
		//有条件选
		private String cardValidBegin;
		private String cardValidEnd;
		@JSONField(name = "EffectNumber")
		private String effectNumber;
		//base64编码，不超过1M
		private String pic;

	}
}
