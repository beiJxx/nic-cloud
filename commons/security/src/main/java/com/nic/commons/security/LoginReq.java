package com.nic.commons.security;

import lombok.Data;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/5 15:15
 */
@Data
public class LoginReq {

	private String loginNo;
	private String username;
	private String password;

}
