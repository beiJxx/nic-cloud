package com.nic.cloud.service;

import com.alibaba.fastjson.JSONObject;
import com.nic.cloud.config.IMqttSender;
import com.nic.cloud.config.MqttConfig;
import com.nic.cloud.message.request.EditPersonBatchRequest;
import com.nic.cloud.message.request.EditPersonRequest;
import com.nic.cloud.message.request.OnlineConfirmRequest;
import com.nic.cloud.message.request.QueryPersonAllRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/8 15:39
 */
@Service
public class SenderServiceImpl implements SenderService {

	@Autowired
	private IMqttSender iMqttSender;
	@Autowired
	private MqttConfig mqttConfig;

	@Override
	public void editPerson(EditPersonRequest editPersonRequest, List<String> deviceList) {
		deviceList.forEach(d -> {
			iMqttSender.sendToMqtt("mqtt/face/" + d, JSONObject.toJSONString(editPersonRequest));
		});
	}

	@Override
	public void editPersonBatch(EditPersonBatchRequest editPersonBatchRequest, List<String> deviceList) {

		deviceList.forEach(d -> {
			iMqttSender.sendToMqtt("mqtt/face/" + d, JSONObject.toJSONString(editPersonBatchRequest));
		});
	}

	@Override
	public void queryPersonAll(QueryPersonAllRequest queryPersonAllRequest, List<String> deviceList) {
		deviceList.forEach(d -> {
			iMqttSender.sendToMqtt("mqtt/face/" + d, JSONObject.toJSONString(queryPersonAllRequest));
		});
	}

	@Override
	public void onlineConfirm(String facesluiceId) {
		OnlineConfirmRequest onlineConfirmRequest = new OnlineConfirmRequest();
		onlineConfirmRequest.setMessageId("onlineConfirm-" + System.currentTimeMillis());
		OnlineConfirmRequest.Info info = new OnlineConfirmRequest.Info();
		info.setFacesluiceId(facesluiceId);
		onlineConfirmRequest.setInfo(info);
		iMqttSender.sendToMqtt("mqtt/face/basic", JSONObject.toJSONString(onlineConfirmRequest));
	}
}
