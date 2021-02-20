package com.nic.cloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nic.cloud.commons.base.api.ApiCode;
import com.nic.cloud.commons.base.api.ApiResult;
import com.nic.cloud.commons.exception.BizException;
import com.nic.cloud.feign.RoleFeignApi;
import com.nic.cloud.mapper.UserMapper;
import com.nic.cloud.model.User;
import com.nic.cloud.pojo.dto.UserDTO;
import com.nic.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/20 13:54
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserMapper userMapper;
	@Autowired
	private RoleFeignApi roleFeignApi;

	@Transactional
	@Override
	public User addUserAndRole(UserDTO userDTO) {
		User user = BeanUtil.copyProperties(userDTO, User.class);
		userMapper.insert(user);
		ApiResult apiResult = roleFeignApi.addRole();
		if (!apiResult.isSuccess()) {
			throw new BizException(ApiCode.FAIL);
		}
		return user;
	}
}
