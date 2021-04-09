package com.nic.cloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/10 17:10
 */

/**
 * 用户日志
 */
@Data
@TableName(value = "`fa_device_operate_log`")
public class FaDeviceOperateLog implements Serializable {
	public static final String COL_ID = "id";
	public static final String COL_MESSAGE_ID = "message_id";
	public static final String COL_USER_ID = "user_id";
	public static final String COL_DEVICE_ID = "device_id";
	public static final String COL_USERNAME = "username";
	public static final String COL_URL = "url";
	public static final String COL_TITLE = "title";
	public static final String COL_CONTENT = "content";
	public static final String COL_MAX_SEND_COUNT = "max_send_count";
	public static final String COL_IP = "ip";
	public static final String COL_USERAGENT = "useragent";
	public static final String COL_CREATETIME = "createtime";
	public static final String COL_STATUS = "status";
	public static final String COL_UPDATE_TIME = "update_time";
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 消息ID
	 */
	@TableField(value = "`message_id`")
	private String messageId;
	/**
	 * 用户ID
	 */
	@TableField(value = "`user_id`")
	private Integer userId;
	/**
	 * 设备ID
	 */
	@TableField(value = "`device_id`")
	private Integer deviceId;
	/**
	 * 管理员名字
	 */
	@TableField(value = "`username`")
	private String username;
	/**
	 * 操作页面
	 */
	@TableField(value = "`url`")
	private String url;
	/**
	 * 日志标题
	 */
	@TableField(value = "`title`")
	private String title;
	/**
	 * 内容
	 */
	@TableField(value = "`content`")
	private String content;
	/**
	 * 发送次数上限
	 */
	@TableField(value = "`max_send_count`")
	private Integer maxSendCount;
	/**
	 * IP
	 */
	@TableField(value = "`ip`")
	private String ip;
	/**
	 * User-Agent
	 */
	@TableField(value = "`useragent`")
	private String useragent;
	/**
	 * 操作时间
	 */
	@TableField(value = "`createtime`")
	private Date createtime;
	/**
	 * 执行状态:10=创建,20=已发送,30=成功,40=失败,50=撤销
	 */
	@TableField(value = "`status`")
	private Object status;
	@TableField(value = "`updatetime`")
	private Date updateTime;
}