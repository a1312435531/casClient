package com.emily.service.impl;

import com.emily.mapper.ResourceMapper;
import com.emily.mapper.RolePermissionMapper;
import com.emily.pojo.*;
import com.emily.service.ResourceService;
import com.emily.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserService userService;

    @Override
    public List<Resource> getSysList() {

        return resourceMapper.selectByExample(new ResourceExample());
    }

    @Override
    public Resource getByIdentifier(String identifier) {
        ResourceExample resourceExample =new ResourceExample();
        resourceExample.createCriteria().andIdentifierEqualTo(identifier);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        if(resources ==null)
            return null;
        return resources.get(0);
    }

    @Override
    public Resource getById(Long id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertSysList(Resource resource) {
        resourceMapper.insertSelective(resource);
    }

    @Override
    public void updateSysList(Resource resource) {
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public boolean needInterceptor(String requestURI) {
        List<Resource> rs = this.getSysList();
        for (Resource r : rs) {
            String url =r.getUrl();
            if (url!=null&&url.equals(requestURI))
                return true;
        }
        return false;
    }

    @Override
    public Set<String> listPermissionURLs(String userName) {
        Set<String> result = new HashSet<>();
        User user = userService.getByName(userName);
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRidEqualTo(user.getRoleId());
        List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
        for (RolePermission rolePermission : rps) {
            Resource r = resourceMapper.selectByPrimaryKey(rolePermission.getPid());
            result.add(r.getUrl());
        }
        return result;
    }

    @Override
    public List<Resource> getResourcesByRoleId(long id) {
        RolePermissionExample rolePermission=new RolePermissionExample();
        rolePermission.createCriteria().andRidEqualTo(id);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(rolePermission);
        List<Resource> resources=new ArrayList<>();
        for (RolePermission rp:rolePermissions) {
            resources.add(resourceMapper.selectByPrimaryKey(rp.getPid()));
        }
        return resources;
    }


}
