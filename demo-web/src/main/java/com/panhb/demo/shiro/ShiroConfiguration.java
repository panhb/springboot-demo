package com.panhb.demo.shiro;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.panhb.demo.constants.Constants;
import com.panhb.demo.dao.PermissionRepository;
import com.panhb.demo.entity.Permission;
import com.panhb.demo.service.PermissionService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.Filter;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);

	@Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
	
	/**
	* cookie管理器;
	* @return
	 * @throws NoSuchAlgorithmException
	*/
	@Bean
	public CookieRememberMeManager rememberMeManager() {
	    log.info("注入Shiro的记住我(CookieRememberMeManager)管理器-->rememberMeManager", CookieRememberMeManager.class);
	    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	    String aesKey = "wGiHplamyXlVB11UXWol8g==";
	    //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
	    try{
	    	KeyGenerator keygen = KeyGenerator.getInstance("AES");
	 	    SecretKey deskey = keygen.generateKey();
	 	    aesKey = Base64.encodeToString(deskey.getEncoded());
	    }catch(Exception e){
	    	log.error("",e);
	    }
	    log.info("************aesKey"+aesKey);
	    byte[] cipherKey = Base64.decode(aesKey);
	    cookieRememberMeManager.setCipherKey(cipherKey);
	    cookieRememberMeManager.setCookie(rememberMeCookie());
	    return cookieRememberMeManager;
	}
	
	@Bean
	public SimpleCookie rememberMeCookie(){
	    //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
	    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
	    //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
	    simpleCookie.setHttpOnly(true);
	    //记住我cookie生效时间,默认30天 ,单位秒：60 * 60 * 24 * 30
	    simpleCookie.setMaxAge(Constants.DEFAULT_COOKIE_TIME);
	    return simpleCookie;
	}
	
	@Bean(name="sessionManager")
	public DefaultWebSessionManager defaultWebSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setCacheManager(ehCacheManager());
		sessionManager.setGlobalSessionTimeout(Constants.DEFAULT_SESSION_TIME);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		return sessionManager;
	}
	
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}
	
	@Bean(name = "shiroRealm")
	@DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
		ShiroRealm realm = new ShiroRealm(); 
		realm.setCacheManager(ehCacheManager());
//		realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }
	
	@Bean(name = "ehCacheManager")
	@DependsOn("lifecycleBeanPostProcessor")
	public EhCacheManager ehCacheManager(){
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return ehCacheManager;
	}
	
	@Bean(name = "securityManager")
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		securityManager.setCacheManager(ehCacheManager());
		securityManager.setSessionManager(defaultWebSessionManager());
		//注入Cookie(记住我)管理器(remenberMeManager)
	    securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}
	
	@Bean
	@ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
	
	@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return aasa;
    }
	
//	@Bean(name = "shiroFilter")
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(PermissionRepository permissionRepository){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setRedirectUrl("/login");
		filters.put("logout", logoutFilter);
		shiroFilterFactoryBean.setFilters(filters);
		
		Map<String, String> chains = Maps.newHashMap();
		chains.put("/logout", "logout");
		List<Permission> list = permissionRepository.findAll();
		if(!FluentIterable.from(list).isEmpty()){
			list.forEach((p) -> {
				chains.put(p.getUrl(), "anon");
			});
		}
//		chains.put("/**", "authc,perms[user:select]");  示例  按权限控制
//		chains.put("/**", "authc,roles[user]"); 示例  按角色控制
		chains.put("/**", "authc");
		log.info("************初始化的权限控制:"+ JSON.toJSONString(chains));
		shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
		
		return shiroFilterFactoryBean;
	}
}

