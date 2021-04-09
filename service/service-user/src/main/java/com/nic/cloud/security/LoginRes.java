package com.nic.cloud.security;

import lombok.Data;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/24 17:32
 */
@Data
public class LoginRes {

	private String userId;
	private String username;
	private String companyId;
	private String companyName;
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
