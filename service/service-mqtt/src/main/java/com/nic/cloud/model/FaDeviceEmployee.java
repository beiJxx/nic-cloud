package com.nic.cloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/10 16:54
 */
@Data
@TableName(value = "`fa_device_employee`")
public class FaDeviceEmployee implements Serializable {
	public static final String COL_ID = "id";
	public static final String COL_DEVICE_KEY = "device_key";
	public static final String COL_DEVICE_NAME = "device_name";
	public static final String COL_EMPLOYEE_ID = "employee_id";
	public static final String COL_EMPLOYEE_NAME = "employee_name";
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@TableField(value = "`device_key`")
	private String deviceKey;
	@TableField(value = "`device_name`")
	private String deviceName;
	@TableField(value = "`employee_id`")
	private Integer employeeId;
	@TableField(value = "`employee_name`")
	private String employeeName;
	private String url;
}