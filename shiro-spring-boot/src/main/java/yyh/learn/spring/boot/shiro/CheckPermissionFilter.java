package yyh.learn.spring.boot.shiro;


import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义权限校验，校验权限时传入请求路径作为匹配值
 * <p>判断用户是否有请求的url的权限</p>
 *
 * @author yyh
 */
public class CheckPermissionFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = this.getSubject(servletRequest, servletResponse);
        String path = getPathWithinApplication(servletRequest);
        boolean isPermitted = true;
        if (!subject.isPermitted(path)) {
            isPermitted = false;
        }

        return isPermitted;
    }
}
