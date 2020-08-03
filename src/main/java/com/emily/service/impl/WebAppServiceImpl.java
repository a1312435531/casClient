package com.emily.service.impl;

import com.emily.mapper.WebAppMapper;
import com.emily.pojo.WebApp;
import com.emily.pojo.WebAppExample;
import com.emily.service.WebAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebAppServiceImpl implements WebAppService {


    @Autowired
    private WebAppMapper webAppMapper;

    @Override
    public List<WebApp> list() {
        List<WebApp> webApps = webAppMapper.selectByExample(new WebAppExample());
        return webApps;
    }

    @Override
    public WebApp getById(Integer id) {
        WebAppExample webAppExample=new WebAppExample();
        webAppExample.createCriteria().andAppIdEqualTo(id);
        List<WebApp> webApps = webAppMapper.selectByExample(webAppExample);
        if (webApps.isEmpty())
            return null;
        return webApps.get(0);
    }

    @Override
    public void deleteApp(Long id) {

    }

    @Override
    public void saveApp(Long id) {

    }



}
