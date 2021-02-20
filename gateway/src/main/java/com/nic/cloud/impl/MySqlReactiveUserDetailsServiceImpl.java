package com.nic.cloud.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/19 10:16
 */
@Slf4j
@Component
public class MySqlReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

	private static final String USER_NOT_EXISTS = "用户不存在！";
	private static List<String> listUsermane = new ArrayList<>();
	private static List<String> listAuthority = new ArrayList<>();

	static {
		listUsermane.add("111");
		listUsermane.add("222");
		listUsermane.add("333");
	}

	static {
		listAuthority.add("/user/info1");
		listAuthority.add("/user/info2");
		listAuthority.add("/user/info3");
	}

	@Autowired
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		log.info("findByUsername:{}", username);
		MyUserDetails myUserDetails = new MyUserDetails();
		int i = RandomUtil.randomInt(3);
		myUserDetails.setUsername(listUsermane.get(i));
		myUserDetails.setPassword("$2a$10$WAA9FvYtdyCg14CY4DMWveuADuPqUb0TxIEz.DrEg37dKFqLC2J0.");
		myUserDetails.setGrantedAuthorities(listAuthority.get(i));
		log.info("myUserDetails:{}", JSONUtil.toJsonStr(myUserDetails));
		return Mono.just(myUserDetails);
//		return ((UserDetails) myUserDetails);
//		return accountInfoRepository.findByUsername(username)
//				.switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException(USER_NOT_EXISTS))))
//				.doOnNext(u -> log.info(
//						String.format("查询账号成功  user:%s password:%s", u.getUsername(), u.getPassword())))
//				.cast(UserDetails.class);
	}

	@Override
	public Mono<UserDetails> updatePassword(UserDetails userDetails, String newPassword) {
		log.info("[updatePassword] newPassword:{}", newPassword);
		return null;
	}

	/*@Override
	public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
		return accountInfoRepository.findByUsername(user.getUsername())
				.switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException(USER_NOT_EXISTS))))
				.map(foundedUser -> {
					foundedUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
					return foundedUser;
				})
				.flatMap(updatedUser -> accountInfoRepository.save(updatedUser))
				.cast(UserDetails.class);
	}*/
}
