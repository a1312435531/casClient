package com.emily.controller;

import com.emily.pojo.Resource;
import com.emily.pojo.User;
import com.emily.pojo.WebApp;
import com.emily.realm.DatabaseRealm;
import com.emily.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.buji.pac4j.subject.Pac4jPrincipal;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate template;

    @RequestMapping("index")
    public String index(ModelMap map) {
        Subject subject =SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        map.addAttribute("isLogin",authenticated);
        System.out.println(authenticated);
        //获取用户身份
        if(authenticated){
            Pac4jPrincipal p = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);
            if(p != null) {
                User user = userService.getByName(p.getName());
                map.addAttribute("user",user );
                List<Resource> resources = resourceService.getResourcesByRoleId(user.getRoleId());
                for (Resource resource:resources) {
                    if(resource.getIdentifier().equals("root")){
                        map.addAttribute("back",true);
                        break;
                    }
                }

            }
        }else
            map.addAttribute("user",new User());


        //System.out.println(SecurityUtils.getSubject().hasRole("admin"));
        Object forObject = template.getForObject("http://www.zytcas.xyz:8099/cas/selectAllClient", Object.class);
        map.addAttribute("list",forObject);
        return "index";
    }



    @RequestMapping("/")
    public String index2() {
        return "redirect:index";
    }
    /**
     * hello
     * @return
     */


    @RequestMapping("login")
    public String login(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        if (callback!=null) {
            return "redirect:"+callback;
        }
        return "index";
    }

    /**
     * 退出
     * @param session
     * @return
     */
   /* @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        //使用cas退出成功后,跳转到http://cas.client1.com:9001/logout/success
        return "redirect:https://www.zytcas.xyz:8443/cas/logout?service=http://www.casClient1.com:9001/casClient/logout/success";
    }*/

    @RequestMapping("logout/success")
    public String logoutsuccess(HttpSession session) {
        return "logout/success";
    }


}
