package com.emily.realm;

import java.util.*;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;

import com.emily.service.PermissionService;
import com.emily.service.RoleService;

public class DatabaseRealm extends Pac4jRealm {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        Pac4jToken token = (Pac4jToken)authenticationToken;
        LinkedHashMap<String, CommonProfile> profiles = token.getProfiles();
        Pac4jPrincipal principal = new Pac4jPrincipal(profiles, this.getPrincipalNameAttribute());
        PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, this.getName());
        return new SimpleAuthenticationInfo(principalCollection, profiles.hashCode());
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> permissions = new HashSet<>();
        Set<String> roles = new HashSet<>();
        Pac4jPrincipal principal = principals.oneByType(Pac4jPrincipal.class);
        if (principal != null) {
                String userName =principal.getName();
                CommonProfile profile = principal.getProfile();
                if (profile != null) {
                    profile.addPermissions(permissionService.listPermissions(userName));
                    profile.addRoles(roleService.listRoleNames(userName));
                    roles.addAll(profile.getRoles());
                    permissions.addAll(profile.getPermissions());
                }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
 

}
