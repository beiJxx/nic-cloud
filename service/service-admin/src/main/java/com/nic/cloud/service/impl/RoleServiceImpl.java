package com.nic.cloud.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nic.cloud.mapper.RoleMapper;
import com.nic.cloud.model.Role;
import com.nic.cloud.service.RoleService;
/**
 * Description:
 * @author james
 * @date  2021/2/20 13:57
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

}
