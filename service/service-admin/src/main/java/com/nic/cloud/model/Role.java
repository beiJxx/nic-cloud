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
 * @date 2021/2/20 13:57
 */
@Data
@TableName(value = "`role`")
public class Role implements Serializable {
	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@TableField(value = "`name`")
	private String name;
}