package com.emily.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emily.mapper.UserMapper;
import com.emily.mapper.UserRoleMapper;
import com.emily.pojo.User;
import com.emily.pojo.UserExample;
import com.emily.pojo.UserRole;
import com.emily.pojo.UserRoleExample;
import com.emily.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
     
    @Override
    public String getPassword(String name) {
        User user = getByName(name);
        if(null==user)
            return null;
        return user.getPassword();
    }
 
    @Override
    public User getByName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.queryUserRoleMes(example);
        if(users.isEmpty())
            return null;
        return users.get(0);
    }
     
    @Override
    public void add(User u) {
        userMapper.insert(u);
    }
  
    @Override
    public void delete(Long id) {

        userMapper.deleteByPrimaryKey(id);

    }
  
    @Override
    public void update(User u) {
        userMapper.updateByPrimaryKeySelective(u);
    }
  
    @Override
    public User get(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
  
    @Override
    public List<User> list(){
        UserExample example =new UserExample();
        example.setOrderByClause("id");
        return userMapper.selectByExample(example);
  
    }

    @Override
    public List<User> userRoleList(HttpServletRequest request){
        String name = request.getParameter("name");
        String roleId = request.getParameter("roleId");
        int page=Integer.parseInt(request.getParameter("page"));
        int limit= Integer.parseInt(request.getParameter("limit"));
        int start = (page-1)*limit;
        PageHelper.offsetPage(start,limit);
        UserExample userExample=new UserExample();
        if(name!=null&&!name.equals("")){
            userExample.or(userExample.createCriteria().andNicknameLike("%" + name + "%"));
            userExample.or(userExample.createCriteria().andUsernameLike("%" + name + "%"));
        }
        if(roleId!=null&&!roleId.equals("")){
            userExample.createCriteria().andRoleIdEqualTo(Long.parseLong(roleId));
        }
        userExample.setOrderByClause("create_date desc");
        return userMapper.queryUserRoleMes(userExample);
    }

}
