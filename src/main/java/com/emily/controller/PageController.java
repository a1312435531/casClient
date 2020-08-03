package com.emily.controller;

import com.emily.service.UserService;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class PageController {
     
    @Autowired
    private UserService userService;
     
    @RequestMapping("deleteOrder")
    public String deleteOrder(){
        return "deleteOrder";
    }
    @RequestMapping("deleteProduct")
    public String deleteProduct(){
        return "deleteProduct";
    }
    @RequestMapping("listProduct")
    public String listProduct(){
        return "listProduct";
    }
     
    
    @RequestMapping("unauthorized")
    public String noPerms(){
        return "unauthorized";
    }

    @RequestMapping("register/{email}/{password}")
    @ResponseBody
    public String register(@PathVariable String email,@PathVariable String password){
        System.out.println(email);
        System.out.println(password);
        return "register test";
    }

    @RequestMapping("/welcome")
    public String welcome(ModelMap map){
        //获取用户身份
        Pac4jPrincipal p = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);

        if(p != null) {
            map.put("user", userService.getByName(p.getName()));
        }
        return "welcome";
    }
}