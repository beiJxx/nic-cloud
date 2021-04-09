package com.nic.cloud.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.nic.cloud.config.IMqttSender;
import com.nic.cloud.config.MqttConfig;
import com.nic.cloud.message.request.EditPersonBatchRequest;
import com.nic.cloud.message.request.EditPersonRequest;
import com.nic.cloud.service.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/24 9:55
 */
@RestController
@Slf4j
public class MqttController {

	/**
	 * 注入发送MQTT的Bean
	 */
	@Resource
	private IMqttSender iMqttSender;
	@Autowired
	private MqttConfig mqttConfig;
	@Autowired
	private SenderService senderService;

	public static void main(String[] args) {
		ArrayList<Integer> strings = new ArrayList<>();
		/*strings.add(1368942);
		strings.add(1381142);
		strings.add(1389274);
		strings.add(1389273);
		strings.add(1381229);
		strings.add(1381218);
		strings.add(1381159);
		strings.add(1389276);
		strings.add(1389279);
		strings.add(1381211);
		strings.add(1389270);
		strings.add(1466760);
		strings.add(1466842);
		strings.add(1466794);
		strings.add(1466928);
		strings.add(1381135);
		strings.add(1460768);
		strings.add(1466837);
		strings.add(1466850);
		strings.add(1466839);
		strings.add(1466776);
		strings.add(1389287);
		strings.add(1460760);
		strings.add(1389284);
		strings.add(1460750);*/

		strings.add(1389286);
		strings.forEach(k -> {
//			String config = getConfig(k);
//			String config = updateConfig(k);
//			String config = queryPerson(k);
//			String config = upSoundconfig(k);
			String config = null;
			try {
//				config = getConfig(k);
//              config = updateConfig(k);
//              config = queryPerson(k);
//              config = upSoundconfig(k);
//				config = detectFace(k);
//				config = getVersions(k);
				config = getSceneSnap(k);
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("str:{}", config);
			HttpUtil.post("http://47.103.39.44:3000/addons/company/api.openinterface/sendDataToDevice", config);
		});

		/*for (int i = 0; i < 300; i++) {
			cn.hutool.json.JSONObject obj = JSONUtil.createObj();
			obj.putOpt("org_auth_key", "10d6030319ec48086a1155cb1f5baf8b");
			obj.putOpt("name", "abc" + i);
			obj.putOpt("avatar_file", "http://192.168.0.121/uploads/interface/1614667499.png");
			String format = String.format("%04d", i);
			obj.putOpt("mobile", "1761212" + format);
			obj.putOpt("id_number", "32028119991010" + format);
			obj.putOpt("gender", "2");
			String body = JSONUtil.toJsonStr(obj);
			log.info(body);
			String result = HttpUtil.post("http://192.168.0.121/addons/company/api.openinterface/EntryStaffData", body);
			log.info("result:{}", result);
		}*/

		/*for (int i = 6000; i < 6001; i++) {
			String body = bindPerson2Group(String.valueOf(i));
			String result = HttpUtil.post("http://192.168.0.121/addons/company/api.openinterface/BindPerson2Group", body);
			log.info("result:{}", result);
		}*/


		/*INSERT INTO fa_device_operate_log(message_id, user_id, device_id, username, url, title, content) VALUES
				('testDelete-xxx', 6391, 1460750, '马海马里木', 'http://47.103.39.44/uploads/interface/1615364436.png',
						'DelPerson',
						'{"operator":"EditPerson","messageId":"1615364437667-133433","info":{"personId":"6391","customId":"6391","name":"马海马里木","nation":1,"gender":1,"birthday":null,"address":"",
						"idCard":"513434199301158960","EffectNumber":"","cardValidBegin":"2021-03-10 16:20:37","cardValidEnd":"2030-02-26 23:27:55","telnum1":"13550440136","native":"","cardType2":0,
						"cardNum2":"","notes":"","personType":0,"isWy":0,"cardType":0,"picURI":"http://47.103.39.44/uploads/interface/1615364436.png","dwidentity":0}}' );*/


	}

	private static String getSceneSnap(Integer deviceId) {
		String messageId = "GetSceneSnap-" + System.currentTimeMillis();
		cn.hutool.json.JSONObject obj = JSONUtil.createObj();
		obj.putOpt("deviceId", deviceId);
		obj.putOpt("messageId", messageId);
		cn.hutool.json.JSONObject content = JSONUtil.createObj();
		content.putOpt("operator", "GetSceneSnap");
		content.putOpt("messageId", messageId);
		content.putOpt("facesluiceId", deviceId.toString());
		cn.hutool.json.JSONObject info = JSONUtil.createObj();
		info.putOpt("facesluiceId", deviceId.toString());
		info.putOpt("ImgType", 2);
		info.putOpt("ImgQuality", 100);
		content.putOpt("info", info);
		obj.putOpt("content", content);
		return JSONUtil.toJsonStr(obj);
	}

	private static String getVersions(Integer deviceId) {
		cn.hutool.json.JSONObject obj = JSONUtil.createObj();
		obj.putOpt("deviceId", deviceId);
		obj.putOpt("messageId", "Versions-" + System.currentTimeMillis());
		cn.hutool.json.JSONObject content = JSONUtil.createObj();
		content.putOpt("operator", "Versions");
		cn.hutool.json.JSONObject info = JSONUtil.createObj();
		content.putOpt("info", info);
		obj.putOpt("content", content);
		return JSONUtil.toJsonStr(obj);
	}

	private static String getConfig(Integer deviceId) {
		cn.hutool.json.JSONObject obj = JSONUtil.createObj();
		obj.putOpt("deviceId", deviceId);
		obj.putOpt("messageId", "Config-" + System.currentTimeMillis());
		cn.hutool.json.JSONObject content = JSONUtil.createObj();
		content.putOpt("operator", "GetDoorconfig");
		cn.hutool.json.JSONObject info = JSONUtil.createObj();
		content.putOpt("info", info);
		obj.putOpt("content", content);
		return JSONUtil.toJsonStr(obj);
	}

	private static String detectFace(Integer deviceId) throws MalformedURLException {
		cn.hutool.json.JSONObject obj = JSONUtil.createObj();
		obj.putOpt("deviceId", deviceId);
		obj.putOpt("messageId", "DetectFaceFromPic-" + System.currentTimeMillis());
		cn.hutool.json.JSONObject content = JSONUtil.createObj();
		content.putOpt("operator", "DetectFaceFromPic");
		cn.hutool.json.JSONObject info = JSONUtil.createObj();
		String encode = Base64.encode(URLUtil.getStream(new URL("http://47.103.39.44/uploads/interface/1615079287.png")));
		info.putOpt("picinfo", encode);
		content.putOpt("info", info);
		obj.putOpt("content", content);
		return JSONUtil.toJsonStr(obj);
	}

	/**
	 * 更新界面参数
	 *
	 * @param deviceId
	 * @return
	 */
	private static String upSoundconfig(Integer deviceId) {
		cn.hutool.json.JSONObject obj = JSONUtil.createObj();
		obj.putOpt("deviceId", deviceId);
		obj.putOpt("messageId", "Config-" + System.currentTimeMillis());
		cn.hutool.json.JSONObject content = JSONUtil.createObj();
		content.putOpt("operator", "UpSoundconfig");
		cn.hutool.json.JSONObject info = JSONUtil.createObj();
		info.putOpt("IsShowDeviceID", "1");//显示本机ID
		content.putOpt("info", info);
		obj.putOpt("content", content);
		return JSONUtil.toJsonStr(obj);
	}

	private static String queryPerson(Integer deviceId) {
		cn.hutool.json.JSONObject obj = JSONUtil.createObj();
		obj.putOpt("deviceId", deviceId);
		obj.putOpt("messageId", "Config-" + System.currentTimeMillis());
		cn.hutool.json.JSONObject content = JSONUtil.createObj();
		content.putOpt("operator", "QueryPerson");
		cn.hutool.json.JSONObject info = JSONUtil.createObj();
		content.putOpt("info", info);
		obj.putOpt("content", content);
		return JSONUtil.toJsonStr(obj);
	}

	private static String bindPerson2Group(String memberId) {
		cn.hutool.json.JSONObject obj = JSONUtil.createObj();
		obj.putOpt("prj_auth_key", "fcc6ee24a2541c8190aeee865c70c3d7");
		obj.putOpt("member_id", memberId);
		obj.putOpt("member_type", "1");
		obj.putOpt("group_id", "799");
		return JSONUtil.toJsonStr(obj);
	}

	private static String updateConfig(Integer deviceId) {
		cn.hutool.json.JSONObject obj = JSONUtil.createObj();
		obj.putOpt("deviceId", deviceId);
		obj.putOpt("messageId", "Config-" + System.currentTimeMillis());
		cn.hutool.json.JSONObject content = JSONUtil.createObj();
		content.putOpt("operator", "UpDoorconfig");
		cn.hutool.json.JSONObject info = JSONUtil.createObj();
		info.putOpt("FaceThreshold", 85);
		content.putOpt("info", info);
		obj.putOpt("content", content);
		return JSONUtil.toJsonStr(obj);
	}

	/**
	 * 发送MQTT消息
	 *
	 * @param message 消息内容
	 * @return 返回
	 */
	@ResponseBody
	@PostMapping(value = "/mqtt/{topic}")
	public ApiResult sendMqtt(@PathVariable String topic, @RequestBody String message) {
		log.info("topic:{}, message:{}", topic, message);
		mqttConfig.addTopics(topic);
		iMqttSender.sendToMqtt(topic, message);
		return ApiResult.ok();
	}

	@PostMapping("/testSend")
	public ApiResult testSend(@RequestBody EditPersonRequest editPersonRequest) throws InterruptedException {
		for (int j = 0; j < 1; j++) {
			for (int i = 0; i < 50; i++) {
				editPersonRequest.setMessageId(String.valueOf(System.currentTimeMillis()) + i);
				log.info("{}=============================================={}", i, editPersonRequest.getMessageId());
				EditPersonRequest.Info info = editPersonRequest.getInfo();
				info.setCustomId(String.valueOf(System.currentTimeMillis()) + i);
				info.setName("aaa" + i);
				mqttConfig.addTopics("mqtt/face/1389286");
				senderService.editPerson(editPersonRequest, Collections.singletonList("1389286"));
				Thread.sleep(1000L);
			}
			log.info("第{}批次，sleep 10s...", j);
			Thread.sleep(10000L);
		}
		return ApiResult.ok();
	}

	@PostMapping("/testSendBatch")
	public ApiResult testSendBatch() throws InterruptedException {
		mqttConfig.addTopics("mqtt/face/1389286");
		EditPersonBatchRequest editPersonBatchRequest = new EditPersonBatchRequest();
		editPersonBatchRequest.setMessageId("batch-" + System.currentTimeMillis());
		ArrayList<EditPersonBatchRequest.Info> infos = new ArrayList<>();

		Integer count = 0;
		for (int i = 0; i < 1; i++) {
			count++;
			long l = System.currentTimeMillis();
			infos.add(new EditPersonBatchRequest.Info() {{
				setCustomId(l + "");
				setName("name-" + l);
				setPersonType(0);
				setTempCardType(0);
				setPicURI("http://192.168.0.121/uploads/interface/1614822606.png");
			}});
			Thread.sleep(10L);
		}
		editPersonBatchRequest.setPersonNum(count);
		editPersonBatchRequest.setInfo(infos);
		log.info("send persons:{}", JSONObject.toJSONString(editPersonBatchRequest));
		senderService.editPersonBatch(editPersonBatchRequest, Collections.singletonList("1389286"));
		return ApiResult.ok();
	}

}
