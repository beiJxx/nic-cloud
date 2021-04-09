package com.nic.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nic.cloud.model.FaDeviceEmployee;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/10 16:54
 */
public interface FaDeviceEmployeeMapper extends BaseMapper<FaDeviceEmployee> {

	@Select("select feu.id employeeId, feu.name employeeName, feu.avatar_file url, fde.device_key deviceKey from fa_device_employee fde\n" +
			"left join fa_employee_user feu on feu.id = fde.employee_id\n" +
			"where employee_id in (select id from fa_employee_user\n" +
			"                                                       where id not in(select employee_id from fa_employee_group_access)\n" +
			"                                                         and company_id = '68'\n" +
			"                                                         and id in (select employee_id from fa_device_employee))")
	List<FaDeviceEmployee> getInfos();

}