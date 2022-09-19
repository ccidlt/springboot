package com.ds.config.shirotoken;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroTokenConfig {

    /**
     * Shiro内置过滤器，可以实现权限相关的拦截器
     * 常用的过滤器：
     *    non: 无需认证（登录）可以访问
     *    authc: 必须认证才可以访问
     *    user: 如果使用rememberMe的功能可以直接访问
     *    perms： 该资源必须得到资源权限才可以访问 perms[user:add] perms[user:update]
     *    role: 该资源必须得到角色权限才可以访问
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        bean.setLoginUrl("/shiroToken/login");
        // 添加自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("tokenFilter", new TokenFilter());
        bean.setFilters(filterMap);
        /**
         * 自定义拦截规则
         */
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/shiroToken/login","anon");//不需要认证，可以直接访问的
        // 其余请求都要经过BearerTokenFilter自定义拦截器
        filterRuleMap.put("/shiroToken/**", "tokenFilter");
        bean.setFilterChainDefinitionMap(filterRuleMap);
        return bean;
    }

    @Bean("webSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myUserRealm") UserRealm userRealm){
        /**
         * 托管userrealm
         */
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        userRealm.setAuthenticationTokenClass(JwtToken.class);//如果不进行设置就默认UsernamePasswordToken
        securityManager.setRealm(userRealm);
        /**
         * 禁用session
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 自定义realm
     * @return
     */
    @Bean("myUserRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }


    //开启shiro aop注解支持  作用在方法上
    @Bean("attributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 让注解权限生效(如果注解权限不生效)
     * @return
     */
    @Bean("advisorAutoProxyCreator")
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

}
