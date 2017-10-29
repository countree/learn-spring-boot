package yyh.learn.spring.boot.config.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的所有配置
 *
 * @author yyh
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建shiroFilter
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        Map<String, Filter> shiroFilters = shiroFilterFactoryBean.getFilters();
        //增加自定义的filter到shiroFilter链
        shiroFilters.put("urlPerms", new CheckPermissionFilter());
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/403", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc,urlPerms[]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public AuthorizingRealm getMyAuthRealm() {
        //因为realm需要 自动注入service所以必须由spring管理realm
        AuthorizingRealm userRealm = new MyAuthRealm();
        //设置自定义的密码对比方法
        userRealm.setCredentialsMatcher(new CustomCredentialsMatcher());
//        //启用缓存,默认false
//        userRealm.setCachingEnabled(true);
//        //  启用身份验证缓存，即缓存AuthenticationInfo信息，默认false；
//        userRealm.setAuthenticationCachingEnabled(true);
//        //  缓存AuthenticationInfo信息的缓存名称,即配置在ehcache.xml中的cache name
//        userRealm.setAuthenticationCacheName("authenticationCache");
//        //  启用授权缓存，即缓存AuthorizationInfo信息，默认true； 默认值根据版本不同可能有不一样
        userRealm.setAuthorizationCachingEnabled(true);
        //  缓存AuthorizationInfo信息的缓存名称；
        userRealm.setAuthorizationCacheName("authorizationCache");
        return userRealm;
    }

    /**
     * securityManager 配置
     *
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setRealm(getMyAuthRealm());
        // 设置session管理器
        securityManager.setSessionManager(sessionManager());
        // 设置缓存管理器
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * ehcacheManager 配置
     *
     * @return
     */
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
        return cacheManager;
    }

    /**
     * session 管理器
     *
     * @return
     */
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 全局session过期时间设置为20分钟
        sessionManager.setGlobalSessionTimeout(20 * 60 * 1000L);
        return sessionManager;
    }

    /**
     * 开启shiro aop注解支持.使用代理方式;所以需要开启代码支持;<br/>
     * 代理@RequiresPermissions注解的方法
     * 目前没用到
     *
     * @param securityManager
     * @return
     */
//    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
//    @Bean(name = "lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
//        daap.setProxyTargetClass(true);
//        return daap;
//    }

}
