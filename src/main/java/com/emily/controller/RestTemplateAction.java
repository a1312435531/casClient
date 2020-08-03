package com.emily.controller;

import com.emily.pojo.WebApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class RestTemplateAction {

    @Autowired
    private RestTemplate template;

    @RequestMapping("deleteClient/{id}")
    @ResponseBody
     public String RestTem(@PathVariable("id") Long id) {
        template.delete("http://www.zytcas.xyz:8099/cas/deleteClient/{id}", id);
        return "delete success";
    }

    @RequestMapping("addClient/{url}/{name}")
    @ResponseBody
    public String RestTems(@PathVariable("url") String url,
                           @PathVariable("name") String name) {
        String restUrl = "http://www.zytcas.xyz:8099/cas/addClient/"+url+"/"+name;
        System.out.println(restUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        //map.add("email", "844072586@qq.com");
        //HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = template.getForEntity( restUrl, String.class );
        System.out.println(response.getBody());
        return "add success";
    }

    @RequestMapping("selectAllClient")
    @ResponseBody
    public Map<String, Object> selectAllClient(){
        String restUrl = "http://www.zytcas.xyz:8099/cas/selectAllClient";

        ResponseEntity<String> response = template.exchange( restUrl, HttpMethod.GET, null, String.class );
        Object forObject = template.getForObject(restUrl, Object.class);
        //System.out.println(response.getBody());
        ResponseEntity<Integer> forEntity = template.getForEntity("http://www.zytcas.xyz:8099/cas/clientCount", Integer.class);

        //System.out.println(forEntity.getBody());
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("data", forObject);
        hashMap.put("msg", "");
        hashMap.put("count",forEntity.getBody());
        hashMap.put("code", 0);

        return hashMap;
    }


    @RequestMapping("findClient/{id}")
    @ResponseBody
    public Object findClient(@PathVariable Long id){
        String restUrl = "http://www.zytcas.xyz:8099/cas/findClient/"+id;
        Object forObject = template.getForObject(restUrl, Object.class);
        System.out.println(forObject);
        return forObject;
    }
}
