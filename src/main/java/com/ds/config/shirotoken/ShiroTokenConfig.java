package com.ds.config.shirotoken;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroTokenConfig {

    /**
     * Shiro内置过滤器，可以实现权限相关的拦截器
     * 常用的过滤器：
     * non: 无需认证（登录）可以访问
     * authc: 必须认证才可以访问
     * user: 如果使用rememberMe的功能可以直接访问
     * perms： 该资源必须得到资源权限才可以访问 perms[user:add] perms[user:update]
     * role: 该资源必须得到角色权限才可以访问
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        //loginUrl：没有登录的用户请求需要登录的页面时自动跳转到登录页面。
        //unauthorizedUrl：没有权限默认跳转的页面，登录的用户访问了没有被授权的资源自动跳转到的页面。
        //其他的一些配置，如下：
        //successUrl：登录成功默认跳转页面，不配置则跳转至”/”，可以不配置，直接通过代码进行处理。
        //securityManager：这个属性是必须的，配置为securityManager就好了。
        //filterChainDefinitions：配置过滤规则，从上到下的顺序匹配。

        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        // 添加自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("tokenFilter", new TokenFilter());
        bean.setFilters(filterMap);

        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/shiroToken/login", "anon");//不需要认证，可以直接访问的
        filterRuleMap.put("/static/**", "anon");
        filterRuleMap.put("/public/**", "anon");
        // 其余请求都要经过BearerTokenFilter自定义拦截器
        filterRuleMap.put("/shiroToken/**", "tokenFilter");
        bean.setFilterChainDefinitionMap(filterRuleMap);
        return bean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getUserRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(userRealm);

        //关闭session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 自定义realm
     *
     * @return
     */
    @Bean
    public UserRealm getUserRealm() {
        return new UserRealm();
    }


    /**
     * 添加注解支持，如果不加的话很有可能注解失效
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager securityManager) {

        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
