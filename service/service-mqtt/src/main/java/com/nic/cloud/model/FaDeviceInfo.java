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
 * @date 2021/3/10 16:15
 */

/**
 * 设备信息表
 */
@Data
@TableName(value = "`fa_device_info`")
public class FaDeviceInfo implements Serializable {
	public static final String COL_ID = "id";
	public static final String COL_DEVICE_ID = "device_id";
	public static final String COL_DEVICE_KEY = "device_key";
	public static final String COL_MAC = "mac";
	public static final String COL_NAME = "name";
	public static final String COL_TYPE_ID = "type_id";
	public static final String COL_PRODUCT_CODE = "product_code";
	public static final String COL_VERSION = "version";
	public static final String COL_TECHNICAL_PARAMETER = "technical_parameter";
	public static final String COL_GROUP_ID = "group_id";
	public static final String COL_LOCATION = "location";
	public static final String COL_LATITUDE = "latitude";
	public static final String COL_LONGITUDE = "longitude";
	public static final String COL_REMARK = "remark";
	public static final String COL_CIMAGES = "cimages";
	public static final String COL_CREATETIME = "createtime";
	public static final String COL_UPDATETIME = "updatetime";
	public static final String COL_ACT_TIME = "act_time";
	public static final String COL_ONLINE_STATUS = "online_status";
	public static final String COL_IS_DELETED = "is_deleted";
	public static final String COL_STATUS = "status";
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 设备ID
	 */
	@TableField(value = "`device_id`")
	private Integer deviceId;
	@TableField(value = "`device_key`")
	private String deviceKey;
	/**
	 * MAC地址
	 */
	@TableField(value = "`mac`")
	private String mac;
	/**
	 * 名称
	 */
	@TableField(value = "`name`")
	private String name;
	/**
	 * 设备类型
	 */
	@TableField(value = "`type_id`")
	private Integer typeId;
	/**
	 * 编码
	 */
	@TableField(value = "`product_code`")
	private String productCode;
	/**
	 * 版本
	 */
	@TableField(value = "`version`")
	private String version;
	/**
	 * 技术参数
	 */
	@TableField(value = "`technical_parameter`")
	private String technicalParameter;
	/**
	 * 设备组ID
	 */
	@TableField(value = "`group_id`")
	private Integer groupId;
	/**
	 * 设备位置
	 */
	@TableField(value = "`location`")
	private String location;
	/**
	 * 纬度
	 */
	@TableField(value = "`latitude`")
	private String latitude;
	/**
	 * 纬度
	 */
	@TableField(value = "`longitude`")
	private String longitude;
	/**
	 * 备注
	 */
	@TableField(value = "`remark`")
	private String remark;
	/**
	 * 设备图片
	 */
	@TableField(value = "`cimages`")
	private String cimages;
	/**
	 * 创建时间
	 */
	@TableField(value = "`createtime`")
	private Integer createtime;
	/**
	 * 更新时间
	 */
	@TableField(value = "`updatetime`")
	private Integer updatetime;
	/**
	 * 激活时间
	 */
	@TableField(value = "`act_time`")
	private Integer actTime;
	/**
	 * 在线状态
	 */
	@TableField(value = "`online_status`")
	private Boolean onlineStatus;
	/**
	 * 是否删除
	 */
	@TableField(value = "`is_deleted`")
	private Object isDeleted;
	/**
	 * 状态
	 */
	@TableField(value = "`status`")
	private Object status;
}