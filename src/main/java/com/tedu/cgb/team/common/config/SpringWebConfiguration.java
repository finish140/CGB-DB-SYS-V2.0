package com.tedu.cgb.team.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tedu.cgb.team.common.web.TimeAccessInterceptor;

@Configuration
public class SpringWebConfiguration implements WebMvcConfigurer {
	
	/**
	 * 注册拦截器并指定拦截规则
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TimeAccessInterceptor())
				.addPathPatterns("/sys/user/doLogin");
	}
	
}
