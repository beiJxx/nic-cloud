package com.nic.commons.security;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author james
 * @date 2020/8/27 13:28
 */
@Component
@Slf4j
public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence charSequence) {
		String password = charSequence.toString();
		log.info("[CustomPasswordEncoder] encoder password:{}", password);
		return SecureUtil.md5(password);
//		return SecureUtil.aes(ifcCoreProperties.getAesKey().getBytes()).encryptBase64(password);
	}

	/**
	 * @param charSequence 前端传过来的
	 * @param s            数据库的
	 * @return
	 */
	@Override
	public boolean matches(CharSequence charSequence, String s) {
		log.info("[MyPasswordEncoder] matches >>> charSequence:{},s:{}", charSequence, s);
		return s.equals(charSequence.toString());
	}
}
