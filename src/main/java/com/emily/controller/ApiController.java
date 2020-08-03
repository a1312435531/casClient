package com.emily.controller;

import com.emily.pojo.User;
import com.emily.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

    @Autowired
    private UserService userService;

    @RequestMapping("api/user/{username}")
    @ResponseBody
    public Object register(@PathVariable String username){
        return userService.getByName(username);
    }
}
