package com.nic.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nic.cloud.model.User;
import com.nic.cloud.pojo.dto.UserDTO;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/20 13:54
 */
public interface UserService extends IService<User> {

	User addUserAndRole(UserDTO userDTO);

}
