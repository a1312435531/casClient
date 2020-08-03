package com.emily.controller;

import com.emily.mapper.ResourceMapper;
import com.emily.pojo.Resource;
import com.emily.pojo.ResourceExample;
import com.emily.pojo.User;
import com.emily.service.ResourceService;
import com.emily.service.UserService;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceMapper resourceMapper;

    @RequestMapping("/back")
    public String list(ModelMap map){
        //获取用户身份
        Pac4jPrincipal p = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);

        if(p != null) {
            map.addAttribute("user", userService.getByName(p.getName()));
        }
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipals().oneByType(Pac4jPrincipal.class).getName();
        User user = userService.getByName(username);
        List<Resource> resources = resourceService.getResourcesByRoleId(user.getRoleId());
        map.addAttribute("list", resources);
        Resource root = resourceService.getByIdentifier("root");
        map.addAttribute("root",root);
        return "back";
    }


    @RequestMapping("/config/resource")
    public String resource(ModelMap map){

        return "resource/resource";
    }

    @ResponseBody
    @RequestMapping("/loadList")
    public Object loadList(){

        return resourceService.getSysList();
    }

    @RequestMapping("/saveResourcePage")
    public String saveResourcePage(HttpServletRequest request,ModelMap map){
        String id = request.getParameter("id");
        String pId = request.getParameter("pId");
        if(id!=null&&!id.equals("")){
            Resource byId = resourceService.getById(Long.parseLong(id));
            map.addAttribute("resource",byId);
        }else {
            map.addAttribute("resource",new Resource());
        }
        map.addAttribute("pId",pId);
        return "resource/resource_detail";
    }

    @RequestMapping("/saveResource")
    @ResponseBody
    public Long saveResource(@RequestBody Resource resource){
        if (resource.getId()==null) {
            resourceService.insertSysList(resource);
        }else {
            resourceService.updateSysList(resource);
        }
        ResourceExample resourceExample =new ResourceExample();
        resourceExample.createCriteria().andNameEqualTo(resource.getName());
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        if(resources !=null)
            return resources.get(0).getId();
        return null;
    }

    @RequestMapping("/deleteResource/{id}")
    @ResponseBody
    public boolean deleteResource(@PathVariable Long id){
        resourceMapper.deleteByPrimaryKey(id);
        return true;
    }


}
