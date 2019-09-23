package com.tedu.cgb.team.common.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.tedu.cgb.team.common.annotation.OperationLogger;
import com.tedu.cgb.team.common.entity.Logger;
import com.tedu.cgb.team.common.util.IPUtils;
import com.tedu.cgb.team.common.util.ShiroUtils;
import com.tedu.cgb.team.sys.service.SysLoggerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Aspect 描述的类为切面类，此类中实现：
 * 1）切入点（Pointcut）的定义
 * 2）通知（Advice）的定义（扩展功能）
 * @version 1.0.20190911
 * @author David Zhao
 *
 */

@Component
@Aspect
@Slf4j
public class LoggerAspect {
	@Autowired
	private SysLoggerService sysLogService;
	
	/**
	 * @Pointcut 注解用于描述或定义一个切入点，
	 * 切入点的定义需要遵循Spring中指定的表达式规范，
	 * 例如：bean(sysMenuServiceImpl)
	 */
	// bean(bean名称或一个表达式)
	@Pointcut("@annotation(com.tedu.cgb.team.common.annotation.OperationLogger)")
	public void logPointcut() {}
	
	/**
	 * @Around 注解描述的方法为一个环绕通知方法，
	 * 在此方法中可以添加扩展业务逻辑，
	 * 可以调用下一个切面对象或目标方法
	 * @param jp 连接点
	 * @return
	 * @throws Throwable
	 */
	@Around("logPointcut()")
	public Object aroundAdvice(ProceedingJoinPoint jp) 
			throws Throwable {
		long start = System.currentTimeMillis();
		Object result = jp.proceed(); // 调用下一个切面或目标方法
		long end = System.currentTimeMillis();
		log.info("Execution time: " + (end - start) + "ms");
		insertLogger(jp, (end - start));
		return result;
	}
	
	@Async
	private void insertLogger(ProceedingJoinPoint jp, long time) 
			throws Throwable {
		Logger logger = getReflectDataIntoObject(jp, time);
		sysLogService.insertRecord(logger);
	}

	private Logger getReflectDataIntoObject(ProceedingJoinPoint jp, long time) 
			throws Throwable {
		Class<?> targetCls = jp.getTarget().getClass();
		MethodSignature signature = (MethodSignature) jp.getSignature();
		Class<?>[] parameterTypes = signature.getParameterTypes();
		String methodName = signature.getName();
		OperationLogger annotation = targetCls
				.getDeclaredMethod(methodName, parameterTypes)
				.getAnnotation(OperationLogger.class);
		
		String operation = annotation.value();
		String ip = IPUtils.getIpAddr();
		String username = ShiroUtils.getCurrentUsername();
		
		Logger logger = new Logger();
		logger.setUsername(username);
		logger.setOperation(operation);
		logger.setIp(ip);
		return logger;
	}
}
