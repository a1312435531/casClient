package com.emily.filter;

import java.net.URLEncoder;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.emily.service.ResourceService;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class URLPathMatchingFilter extends PathMatchingFilter {
	
    @Autowired
    private ResourceService resourceService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        String requestURI = getPathWithinApplication(request);
        String rUrl= URLEncoder.encode(requestURI,"utf-8");
        //System.out.println(requestURI);
        Subject subject = SecurityUtils.getSubject();
        // 如果没有登录，就跳转到认证中心
        if (!subject.isAuthenticated()) {
            WebUtils.issueRedirect(request, response, "/login?callback="+rUrl);
            return false;
        }
        // 看看这个路径权限里有没有维护，如果没有维护，一律放行
        boolean needInterceptor = resourceService.needInterceptor(requestURI);
        if (!needInterceptor) {
            return true;
        } else {
            boolean hasPermission = false;
            String userName = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class).getName();
            Set<String> permissionUrls = resourceService.listPermissionURLs(userName);
            for (String url : permissionUrls) {
                // 这就表示当前用户有这个权限
                if (url!=null&&url.equals(requestURI)) {
                    hasPermission = true;
                    break;
                }
            }
            if (hasPermission)
                return true;
            else {
                UnauthorizedException ex = new UnauthorizedException("当前用户没有访问该路径的权限");
                subject.getSession().setAttribute("ex", ex);
                WebUtils.issueRedirect(request, response, "/unauthorized");
                return false;
            }
 
        }

    }
}
