package com.emily.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emily.mapper.PermissionMapper;
import com.emily.mapper.RolePermissionMapper;
import com.emily.pojo.Permission;
import com.emily.pojo.PermissionExample;
import com.emily.pojo.Role;
import com.emily.pojo.RolePermission;
import com.emily.pojo.RolePermissionExample;
import com.emily.service.PermissionService;
import com.emily.service.RoleService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private RoleService roleService;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public Set<String> listPermissions(String userName) {
		Set<String> result = new HashSet<>();
		List<Role> roles = roleService.listRoles(userName);

		List<RolePermission> rolePermissions = new ArrayList<>();

		for (Role role : roles) {
			RolePermissionExample example = new RolePermissionExample();
			example.createCriteria().andRidEqualTo(role.getId());
			List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
			rolePermissions.addAll(rps);
		}

		for (RolePermission rolePermission : rolePermissions) {
			Permission p = permissionMapper.selectByPrimaryKey(rolePermission.getPid());
			result.add(p.getName());
		}

		return result;
	}

	@Override
	public void add(Permission u) {
		permissionMapper.insert(u);
	}

	@Override
	public void delete(Long id) {
		permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Permission u) {
		permissionMapper.updateByPrimaryKeySelective(u);
	}

	@Override
	public Permission get(Long id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Permission> list() {
		PermissionExample example = new PermissionExample();
		example.setOrderByClause("id desc");
		return permissionMapper.selectByExample(example);

	}

	@Override
	public List<Permission> list(Role role) {
		List<Permission> result = new ArrayList<>();
		RolePermissionExample example = new RolePermissionExample();
		example.createCriteria().andRidEqualTo(role.getId());
		List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
		for (RolePermission rolePermission : rps) {
			result.add(permissionMapper.selectByPrimaryKey(rolePermission.getPid()));
		}

		return result;
	}

	@Override
    public boolean needInterceptor(String requestURI) {
        List<Permission> ps = list();
        for (Permission p : ps) {
            if (p.getUrl().equals(requestURI))
                return true;
        }
        return false;
    }
 
    @Override
    public Set<String> listPermissionURLs(String userName) {
        Set<String> result = new HashSet<>();
        List<Role> roles = roleService.listRoles(userName);
 
        List<RolePermission> rolePermissions = new ArrayList<>();
 
        for (Role role : roles) {
            RolePermissionExample example = new RolePermissionExample();
            example.createCriteria().andRidEqualTo(role.getId());
            List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
            rolePermissions.addAll(rps);
        }
 
        for (RolePermission rolePermission : rolePermissions) {
            Permission p = permissionMapper.selectByPrimaryKey(rolePermission.getPid());
            result.add(p.getUrl());
        }
 
        return result;
    }
}
