package com.nic.cloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * Description:
 * @author james
 * @date  2021/2/20 13:54
 */
@ApiModel(value="com-nic-cloud-model-User")
@Data
@TableName(value = "`user`")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Integer id;

    @TableField(value = "`username`")
    @ApiModelProperty(value="")
    private String username;

    @TableField(value = "`password`")
    @ApiModelProperty(value="")
    private String password;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";
}