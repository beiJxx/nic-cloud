package com.nic.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nic.cloud.mapper.FaDeviceEmployeeMapper;
import com.nic.cloud.model.FaDeviceEmployee;
import com.nic.cloud.service.FaDeviceEmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/10 16:15
 */
@Service
public class FaDeviceEmployeeServiceImpl extends ServiceImpl<FaDeviceEmployeeMapper, FaDeviceEmployee> implements FaDeviceEmployeeService {

	@Resource
	private FaDeviceEmployeeMapper deviceEmployeeMapper;

	@Override
	public List<FaDeviceEmployee> getInfos() {
		return deviceEmployeeMapper.getInfos();
	}
}
