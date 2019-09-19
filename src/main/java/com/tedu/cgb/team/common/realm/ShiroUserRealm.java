package com.tedu.cgb.team.common.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tedu.cgb.team.common.dao.UserMapper;
import com.tedu.cgb.team.common.entity.User;
import com.tedu.cgb.team.common.entity.UserExample;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 设置凭证匹配器。
	 * 通过此对象指定加密算法。
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		// 构建凭证匹配器对象
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		// 设置加密算法
		matcher.setHashAlgorithmName("MD5");
		// 设置加密次数
		matcher.setHashIterations(1);
		// 传递凭证匹配器对象
		super.setCredentialsMatcher(matcher);
	}
	
	/**
	 * 负责认证信息的获取及封装
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		UserExample userExample = new UserExample();
		userExample.createCriteria()
		.andUsernameEqualTo(username);
		User user = userMapper.selectByExample(userExample).get(0);
		if (user == null)
			throw new UnknownAccountException();
		
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = 
				new SimpleAuthenticationInfo(
						user, 
						user.getPassword(), 
						credentialsSalt, 
						getName());
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
