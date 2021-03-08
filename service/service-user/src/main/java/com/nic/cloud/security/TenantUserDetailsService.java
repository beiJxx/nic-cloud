package com.nic.cloud.security;

import com.nic.commons.security.AbstractUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/21 15:30
 */
@Service("userDetailsService")
@Slf4j
public class TenantUserDetailsService extends AbstractUserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername:{}", username);

		TenantUserDetails tenantUserDetails = new TenantUserDetails();
		tenantUserDetails.setUsername(username);
		tenantUserDetails.setPassword("111");
		tenantUserDetails.setGrantedAuthorities("info1,info2");
		return tenantUserDetails;
	}
}
