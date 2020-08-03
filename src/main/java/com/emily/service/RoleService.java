package com.emily.service;

import java.util.List;
import java.util.Set;

import com.emily.pojo.Role;
import com.emily.pojo.User;

public interface RoleService {
	/**
	 * 设置用户的角色
	 * @param userName
	 * @return
	 */
	public Set<String> listRoleNames(String userName);
	/**
	 * 获取用户的角色列表
	 * @param userName
	 * @return
	 */
    public List<Role> listRoles(String userName);
    
    /**
     * 获取用户的角色列表
     * @param user
     * @return
     */
    public List<Role> listRoles(User user);
 
    /**
     * 获取角色列表
     * @return
     */
    public List<Role> list();
    public void add(Role role);
    public void delete(Long id);
    public Role get(Long id);
    public void update(Role role);
}
