package com.nic.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.nic.cloud.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/15 14:05
 */
@RestController
public class TestController {

	@Autowired
	SenderService senderService;

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String send(String msg) throws InterruptedException {
		senderService.send("第一次 " + DateUtil.now());
		Thread.sleep(2000L);
		senderService.send("第二次 " + DateUtil.now());
		Thread.sleep(2000L);
		senderService.send("第三次 " + DateUtil.now());
		return "字符串消息发送成功!";
	}

	@RequestMapping(value = "/sendWithTags", method = RequestMethod.GET)
	public String sendWithTags(String msg, String tag) {
		senderService.sendWithTags(msg, tag);
		return "带tag字符串消息发送成功!";
	}

	/*@RequestMapping(value = "/sendObject", method = RequestMethod.GET)
	public String sendObject(int index) {
		senderService.sendObject(new Foo(index, "foo"), "tagObj");
		return "Object对象消息发送成功!";
	}*/

}
