package com.emily.controller;

import com.emily.pojo.WebApp;
import com.emily.service.WebAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
public class WebAppController {

    @Autowired
    private WebAppService webAppService;

    @Autowired
    private RestTemplateAction restTemplateAction;

    @RequestMapping("webAppList")
    public String appList(Model model){
        /*Properties prop = new Properties();

        try {
            prop.load(new FileInputStream("config/url.properties"));
            System.out.println(prop.getProperty("sso.cas.client"));
            model.addAttribute("client",prop.getProperty("sso.cas.client"));
        } catch(Exception e) {
            e.printStackTrace();
        }*/
        return "app/app_list";
    }

    @RequestMapping("webAppDetail")
    public String appDetail(Long id, Model model){
        if (id!=null)
            model.addAttribute("client",restTemplateAction.findClient(id));
        return "app/app_detail";
    }

    @RequestMapping("/webAppListData")
    @ResponseBody
    public Map<String, Object> lists(String test, HttpServletRequest request){
        Map<String, Object> hashMap = new HashMap<>();
        List<WebApp> list = webAppService.list();
        hashMap.put("data", list);
        hashMap.put("msg", "");
        hashMap.put("count", list.size());
        hashMap.put("code", 0);

        return hashMap;
    }
}
