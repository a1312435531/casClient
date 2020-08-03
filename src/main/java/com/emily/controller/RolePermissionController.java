package com.emily.controller;

import com.emily.mapper.ResourceMapper;
import com.emily.mapper.RolePermissionMapper;
import com.emily.pojo.Resource;
import com.emily.pojo.Role;
import com.emily.pojo.RolePermission;
import com.emily.pojo.RolePermissionExample;
import com.emily.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class RolePermissionController {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/rolePermission")
    public String test(Model m, HttpServletRequest request) {
        String id = request.getParameter("id");
        if(id!=null){
            m.addAttribute("id",id);
        }

        return "rolePermission/rolePermission";
    }

    @ResponseBody
    @RequestMapping("/getRoles")
    public Object getRoles(){
        return roleService.list();
    }

    @ResponseBody
    @RequestMapping("/saveRolePermission/{id}")
    public boolean saveRolePermission(@PathVariable long id, @RequestBody long[] pIds){
        //删除当前角色所有的权限
        RolePermissionExample example= new RolePermissionExample();
        example.createCriteria().andRidEqualTo(id);
        List<RolePermission> rps= rolePermissionMapper.selectByExample(example);
        for (RolePermission rolePermission : rps)
            rolePermissionMapper.deleteByPrimaryKey(rolePermission.getId());

        //设置新的权限关系
        if(null!=pIds)
            for (long pid : pIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPid(pid);
                rolePermission.setRid(id);
                rolePermissionMapper.insert(rolePermission);
            }
        return true;
    }

    @ResponseBody
    @RequestMapping("/getRolePermission/{id}")
    public Object getRolePermission(@PathVariable long id){
        RolePermissionExample example= new RolePermissionExample();
        example.createCriteria().andRidEqualTo(id);
        List<RolePermission> rps= rolePermissionMapper.selectByExample(example);
        List<Long> longs =new ArrayList<>();
        for (RolePermission rolePermission : rps){
            longs.add(rolePermission.getPid());
        }
        return longs;
    }

}
