package com.nic.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nic.cloud.model.FaDeviceEmployee;

import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/10 16:15
 */
public interface FaDeviceEmployeeService extends IService<FaDeviceEmployee> {
	List<FaDeviceEmployee> getInfos();

}
