package com.nic.cloud.commons.base.login;

import lombok.Data;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/1 9:44
 */
public class UserInfoContext {

	private static ThreadLocal threadLocal = new ThreadLocal<UserInfo>();

	public UserInfoContext() {

	}

	public static UserInfo get() {
		return (UserInfo) threadLocal.get();
	}

	public static void set(UserInfo userInfo) {
		threadLocal.set(userInfo);
	}

	public static void remove() {
		threadLocal.remove();
	}

	@Data
	public static class UserInfo {
		private Integer id;
		private String name;
		private String agentNo;
		private String tenantNo;
	}
}
