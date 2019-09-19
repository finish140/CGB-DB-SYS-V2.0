package com.tedu.cgb.team.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tedu.cgb.team.common.entity.User;
import com.tedu.cgb.team.common.vo.JsonResult;
import com.tedu.cgb.team.common.vo.Page;
import com.tedu.cgb.team.sys.service.UserService;

@RestController
@RequestMapping("/sys/user/")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		Page<User> page = userService.findPageObjects(username, pageCurrent);
		return new JsonResult(page);
	}
	
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(User user) {
		userService.saveObject(user);
		return new JsonResult("保存成功");
	}
	
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(Integer id) {
		User result = userService.findObjectById(id);
		return new JsonResult(result);
	}
	
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateUserById(User user) {
		userService.updateById(user);
		return new JsonResult("更新成功");
	}
	
	@RequestMapping("doLogin")
	public JsonResult doLogin(
			boolean isRememberMe, 
			String username, 
			String password) {
		// 把数据封装到token对象
		UsernamePasswordToken token = 
				new UsernamePasswordToken(username, password);
		token.setRememberMe(isRememberMe);
		
		// 获取subject对象
		Subject subject = SecurityUtils.getSubject();
		// 把token对象提交到SecurityManager对象
		subject.login(token);
		return new JsonResult("登录成功");
	}
	
	@RequestMapping("doUpdatePassword")
	public JsonResult doUpdatePassword(
			@RequestParam("pwd") String password, 
			@RequestParam("newPwd") String newPassword,
			@RequestParam("cfgPwd") String confirmPassword) {
		userService.updatePasswordById(password, newPassword, confirmPassword);
		return new JsonResult("修改成功");
	}
}
