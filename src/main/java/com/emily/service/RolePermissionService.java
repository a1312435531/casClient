package com.emily.service;

import com.emily.pojo.Role;

public interface RolePermissionService {
	/**
	 * 设置角色拥有的权限
	 * @param role
	 * @param permissionIds
	 */
	public void setPermissions(Role role, long[] permissionIds);
	/**
	 * 
	 * @param roleId
	 */
    public void deleteByRole(long roleId);
    /**
     * 
     * @param permissionId
     */
    public void deleteByPermission(long permissionId);
}
