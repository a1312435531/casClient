package com.emily.service;

import com.emily.pojo.User;

public interface UserRoleService {
	/**
	 * 给用户设置角色
	 * @param user
	 * @param roleIds
	 */
	public void setRoles(User user, long[] roleIds);
	
	/**
	 * 删除该用户所有角色
	 * @param userId
	 */
    public void deleteByUser(long userId);
    
    /**
     * 删除角色
     * @param roleId
     */
    public void deleteByRole(long roleId);
}
