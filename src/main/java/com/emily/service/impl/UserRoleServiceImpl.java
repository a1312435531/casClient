package com.emily.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emily.mapper.UserRoleMapper;
import com.emily.pojo.User;
import com.emily.pojo.UserRole;
import com.emily.pojo.UserRoleExample;
import com.emily.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired 
	private UserRoleMapper userRoleMapper;
    @Override
    public void setRoles(User user, long[] roleIds) {
        //删除当前用户所有的角色
        UserRoleExample example= new UserRoleExample();
        example.createCriteria().andUidEqualTo(user.getId());
        List<UserRole> urs= userRoleMapper.selectByExample(example);
        for (UserRole userRole : urs)
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
 
        //设置新的角色关系
        if(null!=roleIds)
            for (long rid : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setRid(rid);
                userRole.setUid(user.getId());
                userRoleMapper.insert(userRole);
            }
    }
    @Override
    public void deleteByUser(long userId) {
        UserRoleExample example= new UserRoleExample();
        example.createCriteria().andUidEqualTo(userId);
        userRoleMapper.deleteByExample(example);
       /* List<UserRole> urs= userRoleMapper.selectByExample(example);
        for (UserRole userRole : urs) {
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
        }*/
    }
    @Override  
    public void deleteByRole(long roleId) {
        UserRoleExample example= new UserRoleExample();
        example.createCriteria().andRidEqualTo(roleId);
        List<UserRole> urs= userRoleMapper.selectByExample(example);
        for (UserRole userRole : urs) {
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
        }
    }

}
