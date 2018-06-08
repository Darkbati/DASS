package com.liberty.dataserver.database;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 1)
public class ExecutionLoggingAspect implements InitializingBean {

	@Around("execution(* com.liberty..*service.*(..))")
	public Object doServiceProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
		final String methodName = joinPoint.getSignature().getName();
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
		}

		/*
		DataSource dataSource = (DataSource) method.getAnnotation(DataSource.class);
		if (dataSource != null) {
			DatabaseContextHolder.setDataSourceType(dataSource.value());
		} else {
			if (!(method.getName().startsWith("get") || method.getName().startsWith("select"))) {
				DatabaseContextHolder.setDataSourceType(DatabaseSourceType.Master);
			}
		}
		*/

		Object returnValue = joinPoint.proceed();
		DatabaseContextHolder.clearDataSourceType();
		return returnValue;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
