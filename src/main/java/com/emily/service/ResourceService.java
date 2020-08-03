package com.emily.service;

import com.emily.pojo.Resource;

import java.util.List;
import java.util.Set;

public interface ResourceService {

    public List<Resource> getSysList();


    public Resource getByIdentifier(String identifier);

    public Resource getById(Long id);

    public void insertSysList(Resource resource);

    public void updateSysList(Resource resource);

    /**
     * 表示是否要进行拦截
     * @param requestURI
     * @return
     */
    public boolean needInterceptor(String requestURI);

    /**
     * 用来获取某个用户所拥有的权限地址集合
     * @param userName
     * @return
     */
    public Set<String> listPermissionURLs(String userName);

    public List<Resource> getResourcesByRoleId(long id);
}
