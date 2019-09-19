package com.tedu.cgb.team.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.tedu.cgb.team.common.exception.AccessDeniedException;

public class TimeAccessInterceptor implements HandlerInterceptor {

	/**
	 * 此方法在Handler对象方法执行之前执行，
	 * 此方法的返回值决定了目标Handler的方法是否执行
	 * true放行，false拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		// 获取允许访问的开始时间
		long start = calendar.getTimeInMillis();
		// 获取允许访问的结束时间
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		long end = calendar.getTimeInMillis();
		// 获取当前时间
		long current = System.currentTimeMillis();
		if (current < start || current > end) {
			throw new AccessDeniedException("此时间段不允许访问");
		}
		return true; 
	}

}
