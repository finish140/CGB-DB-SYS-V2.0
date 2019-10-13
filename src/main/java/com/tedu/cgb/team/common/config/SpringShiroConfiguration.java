package com.tedu.cgb.team.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SpringShiroConfiguration {
	
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(
			SecurityManager securityManager) {
		// 构建bean对象，通过此对象创建过滤器工厂
		ShiroFilterFactoryBean factoryBean = 
				new ShiroFilterFactoryBean();
		// 注入SecurityManager
		factoryBean.setSecurityManager(securityManager);
		// 设置登录url
		factoryBean.setLoginUrl("/sys/doLoginUI");
		
		// 设置过滤器规则
		Map<String, String> map = 
				new LinkedHashMap<>();
		// anon  表示允许不认证匿名访问
		// authc 表示需要认证访问
		map.put("/css/**", "anon");
		map.put("/js/**", "anon");
		map.put("/img/**", "anon");
		map.put("/img2/**", "anon");
		map.put("/image/**", "anon");
		map.put("/webfonts/**", "anon");
		map.put("/sys/bower_components/**", "anon");
		map.put("/sys/build/**", "anon");
		map.put("/sys/dist/**", "anon");
		map.put("/sys/plugins/**", "anon");
		map.put("/sys/user/doLogin","anon");
		map.put("/sys/doLogout","logout");
		map.put("/**", "user");
		factoryBean.setFilterChainDefinitionMap(map);
		return factoryBean;
	}
	
	
//	----------------------------------------------------------------------
//	== 授 权 配 置 ======================================================== 
//	----------------------------------------------------------------------
	
	
	/**
	 * 配置Shiro框架中bean对象的生命周期管理器
	 * 此对象的编写需要根据Spring的标准编写
	 * @return
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor 
	lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	/**
	 * 此对象会在Spring容器启动式扫描所有的Advisor对象，
	 * 基于Advisor对象的切入点的描述，进行代理对象的创建
	 * @return
	 */
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator 
	defaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	
	/**
	 * 配置Advisor对象，在此对象中定义切入点以及要在通知里进行实现功能扩展（advice）
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor 
	authorizationAttributeSourceAdvisor(
			@Autowired SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = 
				new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	
	
//	----------------------------------------------------------------------
//	== SecurityManager 配 置 =============================
//	----------------------------------------------------------------------
	
	/**
	 * SecurityManager配置
	 * @param realm
	 * @param cacheManager
	 * @param sessionManager
	 * @param rememberMeManager
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(
		  Realm realm,
		  CacheManager cacheManager,
		  SessionManager sessionManager,
		  RememberMeManager rememberMeManager) {
		DefaultWebSecurityManager securityManager = 
				new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		securityManager.setCacheManager(cacheManager);
		securityManager.setRememberMeManager(rememberMeManager);
		securityManager.setSessionManager(sessionManager);
		return securityManager;
	}
	
	/**
	 * CacheManager配置
	 * @return
	 */
	@Bean
	public CacheManager memoryConstrainedCacheManager() {
		return new MemoryConstrainedCacheManager();
	}
	
	private SimpleCookie simpleCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setMaxAge(10 * 60);
		return cookie;
	}
	
	/**
	 * RememberMeManager配置
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager manager = 
				new CookieRememberMeManager();
		SimpleCookie cookie = simpleCookie();
		manager.setCookie(cookie);
		return manager;
	}
	
	/**
	 * SessionManager配置
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = 
				new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
		return sessionManager;
	}
}
