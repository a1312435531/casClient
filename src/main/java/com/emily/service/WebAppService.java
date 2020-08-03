package com.emily.service;

import com.emily.pojo.WebApp;

import java.util.List;


public interface WebAppService {
    public List<WebApp> list();
    public WebApp getById(Integer id);
    public void deleteApp(Long id);
    public void saveApp(Long id);
}
