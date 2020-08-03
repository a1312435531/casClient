package com.emily.service;

import java.util.List;
import java.util.Set;

import com.emily.pojo.Permission;
import com.emily.pojo.Role;

public interface PermissionService {
	/**
	 * 根据用户名获取权限列表
	 * @param userName
	 * @return
	 */
	public Set<String> listPermissions(String userName);
	 
	/**
	 * 获取权限列表
	 * @return
	 */
    public List<Permission> list();
    
    /**
     * 新增权限
     * @param permission
     */
    public void add(Permission permission);
    
    /**
     * 删除权限
     * @param id
     */
    public void delete(Long id);
    
    /**
     * 获取权限
     * @param id
     * @return
     */
    public Permission get(Long id);
    
    /**
     * 修改权限
     * @param permission
     */
    public void update(Permission permission);
    
    /**
     * 获取角色拥有的权限列表
     * @param role
     * @return
     */
    public List<Permission> list(Role role);
    
    /**
     * 表示是否要进行拦截
     * @param requestURI
     * @return
     */
    public boolean needInterceptor(String requestURI);
    
    /**
     * 用来获取某个用户所拥有的权限地址集合
     * @param userName
     * @return
     */
    public Set<String> listPermissionURLs(String userName);
}
