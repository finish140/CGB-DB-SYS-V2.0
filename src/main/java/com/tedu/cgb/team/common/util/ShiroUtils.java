package com.tedu.cgb.team.common.util;

import org.apache.shiro.SecurityUtils;

import com.tedu.cgb.team.common.entity.User;

public class ShiroUtils {
	
	public static String getCurrentUsername() {
		String username = ((User) SecurityUtils.getSubject()
				.getPrincipal())
				.getUsername();
		return username;
	}
	
	public static User getCurrentUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}
}
