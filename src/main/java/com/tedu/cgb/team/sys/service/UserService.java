package com.tedu.cgb.team.sys.service;


import com.tedu.cgb.team.common.entity.User;
import com.tedu.cgb.team.common.vo.Page;

public interface UserService {
	Page<User> findPageObjects(String username, Integer pageCurrent);
	
	int saveObject(User user);
	
	User findObjectById(Integer id);
	
	int updateById(User user);
	
	/**
	 * 根据id更新对应的加密密码
	 * @param oldPassword 用户输入的原密码
	 * @param newPassword 用户输入的新密码
	 * @param confirmPassword 用户输入的新密码确认
	 * @return
	 */
	int updatePasswordById(String oldPassword, String newPassword, String confirmPassword);
}
