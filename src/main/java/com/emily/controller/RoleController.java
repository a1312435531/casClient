package com.emily.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emily.pojo.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emily.pojo.Permission;
import com.emily.pojo.Role;
import com.emily.service.PermissionService;
import com.emily.service.RolePermissionService;
import com.emily.service.RoleService;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("config")
public class RoleController {
    @Autowired RoleService roleService;
    @Autowired RolePermissionService rolePermissionService;
    @Autowired PermissionService permissionService;
     
    @RequestMapping("listRole")
    public String list(Model model){

        return "role/role_list";
    }
    @ResponseBody
    @RequestMapping("listRoleData")
    public Map<String, Object> lists(HttpServletRequest request){
        Map<String, Object> hashMap = new HashMap<>();
        List<Role> roles = roleService.list();
        hashMap.put("data", roles);
        hashMap.put("msg", "");
        hashMap.put("count", roles.size());
        hashMap.put("code", 0);
        return hashMap;
    }

    @RequestMapping("roleDetail")
    public String userDetail(Long id,Integer action,Model model){
        if (action!=null){
            model.addAttribute("action",action);
        }
        try {
            if (id!=null){
                Role role = roleService.get(id);
                model.addAttribute("role",role);
            }else {
                model.addAttribute("role",new Role());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return "role/role_detail";
    }

    @RequestMapping("deleteRole")
    public String delete(Model model,long id){
        roleService.delete(id);
        return "redirect:listRole";
    }
    @RequestMapping("saveRole")
    @ResponseBody
    public String save(@NotNull @RequestBody Role role){
        try {
            if (role.getId()==null){
                Role role1 = new Role();
                role1.setIdentifier(role.getIdentifier());
                role1.setName(role.getName());
                role1.setRemark(role.getRemark());
                roleService.add(role1);
            }else {
                roleService.update(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return "success";
    }
 
}
