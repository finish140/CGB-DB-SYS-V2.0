package com.tedu.cgb.team.common.web;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tedu.cgb.team.common.vo.JsonResult;

/**
 * 当控制层对象出现异常后，
 * 1) 检测控制层对象内有没有@ExceptionHandler描述的异常处理方法；
 * 2) 检测Spring容器中是否有对象使用了@ControllerAdvice注解修饰，
 *    假如有，则使用类中的@ExceptionHandler处理特定类型的异常。
 * @version 1.0.20190905
 * @author David Zhao
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * @ExceptionHandler 描述的方法为异常处理的方法
	 * 其中注解内部指定的异常类型，为此方法可处理的异常类型
	 */
	@ExceptionHandler(RuntimeException.class)
	public JsonResult doRuntimeException(RuntimeException e) {
		JsonResult result = getExceptionResult();
		
		if (e.getMessage() != null || !StringUtils.isEmpty(e.getMessage())) {
			result.setMessage(e.getMessage());
			return result;
		}else {
			result.setMessage("系统维护中");
		}
		return result;
	}
	
	/**
	 * 负责处理认证异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AuthenticationException.class)
	public JsonResult doAuthenticationException(AuthenticationException e) {
		JsonResult result = getExceptionResult();
		
		if(e.getClass() == UnknownAccountException.class) {
			result.setMessage("账户不存在");
		}else if(e.getClass() ==  LockedAccountException.class) {
			result.setMessage("账户已被禁用");
		}else if(e.getClass() ==  IncorrectCredentialsException.class) {
			result.setMessage("密码不正确");
		}
		return result;
	}
	
	/**
	 * 负责处理授权异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AuthorizationException.class)
	public JsonResult doAuthorizationException(AuthorizationException e) {
		JsonResult result = getExceptionResult();
		result.setMessage("没有此操作权限");
		return result;
	}
	
	private JsonResult getExceptionResult() {
		JsonResult result = new JsonResult();
		result.setState(0);
		return result;
	}
}
