package com.nic.cloud.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/24 17:32
 */
@Data
@ApiModel(value = "登录返回信息")
public class LoginRes {

	@ApiModelProperty("用户ID")
	private String userId;
	@ApiModelProperty("用户名")
	private String username;
	@ApiModelProperty("公司ID")
	private String companyId;
	@ApiModelProperty("公司名")
	private String companyName;
	@ApiModelProperty("token")
	private String token;
//	@ApiModelProperty("菜单列表")
//	private List<Menu> menuList;

//	@Data
//	@JsonInclude(JsonInclude.Include.NON_NULL)
//	public static class Menu {
//		private String id;
//		private String pid;
//		private String name;
//		private String type;
//		private String url;
//		private String sort;
//		private String frontendUrl;
//	}

}
