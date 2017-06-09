package com.panhb.demo.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroRealm extends AuthorizingRealm{
	
	private static final Logger log = LoggerFactory.getLogger(ShiroRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("******执行Shiro权限认证******");
		String username = (String) principals.getPrimaryPrincipal();
		log.info("*****************username"+username);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		String username = upt.getUsername();
		log.info("*****************username"+username);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, "password", getName());
		return info;
	}
}
