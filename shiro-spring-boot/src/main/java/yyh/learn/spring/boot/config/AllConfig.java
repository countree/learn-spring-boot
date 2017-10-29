package yyh.learn.spring.boot.config;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class AllConfig {
    /**
     * 注册shiroFilter到 spring容器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistrationBean.addInitParameter("targetFilterLifecycle", "true");
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
