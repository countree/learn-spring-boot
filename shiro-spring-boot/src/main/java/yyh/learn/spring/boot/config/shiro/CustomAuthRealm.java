package yyh.learn.spring.boot.config.shiro;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import yyh.learn.spring.boot.cache.CacheKey;
import yyh.learn.spring.boot.entity.User;

public class CustomAuthRealm extends AuthorizingRealm {
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        return CacheKey.SHIRO_AUTHORIZATION_PREFIX + user.getName();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("==============doGetAuthorizationInfo 没有走缓存.....");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission("/remove");
        simpleAuthorizationInfo.addStringPermission("/get");
        simpleAuthorizationInfo.addStringPermission("/clear");
        simpleAuthorizationInfo.addStringPermission("/put");
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String name = token.getUsername();
        char[] pwd = token.getPassword();
        User user = new User();
        user.setAge(20);
        user.setName(name);
        user.setPassword(DigestUtils.md5Hex(String.valueOf(pwd)));
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return simpleAuthenticationInfo;
    }

    public void removeCacheAuthorizationInfo() {
        Subject subject = SecurityUtils.getSubject();
        clearCachedAuthorizationInfo(subject.getPrincipals());
    }

    public Object getCacheAuthorizationInfo() {
        Subject subject = SecurityUtils.getSubject();
        return getAuthorizationInfo(subject.getPrincipals());
    }

}
