package com.nic.cloud.commons.base.login;

import lombok.Data;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/5 14:32
 */
@Data
public abstract class AbstractUserDetails {

	public abstract String getUsername();

	public abstract Integer getUserId();


}
