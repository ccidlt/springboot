package com.ds.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /*
     *   倒序配置
     *   1、先自定义过滤器 MyRealm
     *   2、创建第二个DefaultWebSecurityManager，将MyRealm注入
     *   3、装配第三个ShiroFilterFactoryBean，将DefaultWebSecurityManager注入，并注入认证及授权规则
     * */

    /**
     * 装配ShiroFilterFactoryBean，并将 DefaultWebSecurityManager 注入到 ShiroFilterFactoryBean 中
     * Shiro内置过滤器，可以实现权限相关的拦截器
     * 常用的过滤器：
     *    non: 无需认证（登录）可以访问
     *    authc: 必须认证才可以访问
     *    user: 如果使用rememberMe的功能可以直接访问
     *    perms： 该资源必须得到资源权限才可以访问 perms[user:add] perms[user:update]
     *    role: 该资源必须得到角色权限才可以访问
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> map = new LinkedHashMap<>();
        // 有先后顺序
        // 允许匿名访问
        map.put("/shiro/login", "anon");
        //设置注销过滤器
        map.put("/shiro/logout","logout");
        // 进行身份认证后才能访问
        map.put("/shiro/**", "authc");
        // 将拦截器链设置到shiro中
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        // 设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/shiro/login");
//        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/shiro/index");
//        // 设置未授权页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager ，并且将 MyRealm 注入到 DefaultWebSecurityManager bean 中
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 自定义过滤器Realm
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 开启Shiro注解模式，可以在Controller中的方法上添加注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置哈希算法名称
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置哈希迭代次数
        hashedCredentialsMatcher.setHashIterations(1024);
        // 设置存储凭证(true:十六进制编码,false:base64)
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
}
