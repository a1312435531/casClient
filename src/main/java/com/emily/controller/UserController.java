package com.emily.controller;

import com.emily.pojo.Role;
import com.emily.pojo.User;
import com.emily.service.RoleService;
import com.emily.service.UserRoleService;
import com.emily.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.PrintException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("config")
public class UserController {
    @Autowired UserRoleService userRoleService;
    @Autowired UserService userService;
    @Autowired RoleService roleService;



    @RequestMapping("listUser")
    public String list(Model model){
        /*List<User> us= userService.list();
        model.addAttribute("us", us);
        Map<User,List<Role>> user_roles = new HashMap<>();
        for (User user : us) {
            List<Role> roles=roleService.listRoles(user);
            user_roles.put(user, roles);
        }
        model.addAttribute("user_roles", user_roles);*/
        List<Role> roles = roleService.list();
        model.addAttribute("roles",roles);
        return "user/user_list";
    }

    @ResponseBody
    @RequestMapping("listUserData")
    public Map<String, Object> lists(String test, HttpServletRequest request){
        Map<String, Object> hashMap = new HashMap<>();

        List<User> us= userService.userRoleList(request);
        int total = (int) new PageInfo<>(us).getTotal();
        hashMap.put("data", us);
        hashMap.put("msg", "");
        hashMap.put("count", total);
        hashMap.put("code", 0);

        return hashMap;
    }


    @ResponseBody
    @RequestMapping("deleteUser")
    public String delete(@RequestBody  long[] ids){
        for (Long id:ids) {
            userService.delete(id);
        }
        return "delete success";

    }

    @RequestMapping("updateUser")
    public String update(User user,long[] roleIds){
        userRoleService.setRoles(user,roleIds);
         
        String password=user.getPassword();
        //如果在修改的时候没有设置密码，就表示不改动密码
        if(user.getPassword().length()!=0) {
            //String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            //int times = 1;
            String algorithmName = "md5";
            String encodedPassword = new SimpleHash(algorithmName,password).toString();
            user.setPassword(encodedPassword);
        }
        else
            user.setPassword(null);
         
        userService.update(user);
 
        return "redirect:listUser";
 
    }


    @RequestMapping("userDetail")
    public String userDetail(Long id,Integer action,Model model){
        List<Role> roles = roleService.list();
        model.addAttribute("roles",roles);
        if (action!=null){
            model.addAttribute("action",action);
        }
        try {
            if (id!=null){
                User user = userService.get(id);
                model.addAttribute("user",user);
            }else {
                model.addAttribute("user",new User());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
        return "user/user_detail";
    }

    @RequestMapping("userInfo")
    public String userInfo(Long id,Integer action,Model model){
        List<Role> roles = roleService.list();
        model.addAttribute("roles",roles);
        try {
            if (id!=null){
                User user = userService.get(id);
                model.addAttribute("user",user);
            }else {
                model.addAttribute("user",new User());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
        return "user/user_info";
    }

    @RequestMapping("saveUser")
    @ResponseBody
    public boolean save(@NotNull @RequestBody  User user){
        if(userService.getByName(user.getUsername())!=null&&user.getId()==null){
            return false;
        }
        String algorithmName = "md5";
        String encodedPassword="";
        if (user.getPassword()!=null&&!user.getPassword().equals("")){
            encodedPassword = new SimpleHash(algorithmName,user.getPassword()).toString();
        }else{
            encodedPassword = new SimpleHash(algorithmName,"123456").toString();
        }
        try {
            if (user.getId()==null){
                User u = new User();
                u.setCreateDate(new Date());
                u.setUsername(user.getUsername());
                u.setNickname(user.getNickname());
                u.setRoleId(user.getRoleId());
                u.setPassword(encodedPassword);
                u.setGender(user.getGender());
                u.setPhoneNumber(user.getPhoneNumber());
                u.setEmail(user.getEmail());
                userService.add(u);
            }else {
                if (user.getPassword()==null||user.getPassword().equals(""))
                    user.setPassword(userService.get(user.getId()).getPassword());
                else
                    user.setPassword(new SimpleHash(algorithmName,user.getPassword()).toString());
                userService.update(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @RequestMapping("/changePasswordPage")
    public String updatePasswordPage(Long id,Model model){
        model.addAttribute("id",id);
        return "user/change_password";
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public String updatePassword(@NotNull @RequestBody User user){
        User userBy = userService.get(user.getId());
        userBy.setPassword(user.getPassword());
        userService.update(userBy);
        return "change success";
    }

    @RequestMapping("/checkPassword")
    @ResponseBody
    public String checkPassword(Long id){
        User user = userService.get(id);
        return user.getPassword();
    }

    @RequestMapping("/checkUserUnique")
    @ResponseBody
    public boolean checkUsernameUnique(String username){

        return userService.getByName(username)==null;
    }

    @RequestMapping("/userCenter")
    public String userCenter(ModelMap map){
        //获取用户身份
        Pac4jPrincipal p = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);

        if(p != null) {
            map.addAttribute("user", userService.getByName(p.getName()));
        }

        return "user/user_center";
    }
}
