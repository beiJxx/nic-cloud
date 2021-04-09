package com.nic.cloud.service;

import com.nic.cloud.message.request.EditPersonBatchRequest;
import com.nic.cloud.message.request.EditPersonRequest;
import com.nic.cloud.message.request.QueryPersonAllRequest;

import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/8 13:51
 */
public interface SenderService {

	void editPerson(EditPersonRequest editPersonRequest, List<String> deviceList);

	void editPersonBatch(EditPersonBatchRequest editPersonBatchRequest, List<String> deviceList);

	void queryPersonAll(QueryPersonAllRequest queryPersonAllRequest, List<String> deviceList);

	void onlineConfirm(String facesluiceId);

}
