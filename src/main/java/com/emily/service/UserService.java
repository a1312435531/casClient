package com.emily.service;

import java.util.List;

import com.emily.pojo.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
	/**
	 * 获取用户密码
	 * @param name
	 * @return
	 */
	public String getPassword(String name);
	
	/**
	 * 根据用户名获取用户实体
	 * @param username
	 * @return
	 */
    public User getByName(String username);

    /**
     * 获取用户列表
     * @return
     */
    public List<User> list();
    
    
    public void add(User user);
    public void delete(Long id);
    public User get(Long id);
    public void update(User user);

    public List<User> userRoleList(HttpServletRequest request);
}
