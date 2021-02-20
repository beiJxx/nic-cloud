package com.nic.cloud.commons.base.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/18 15:15
 */
@Data
public class LoginRequestDTO {

	@NotBlank(message = "用户名不能为空")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;

}
