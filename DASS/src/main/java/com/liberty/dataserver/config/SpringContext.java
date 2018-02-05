package com.liberty.dataserver.config;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringContext {
	private static GenericApplicationContext ctx;
	private static Object LOCK = new Object();

	public static GenericApplicationContext getContext() {
		if (ctx == null) {
			synchronized (LOCK) {
				ctx = new GenericXmlApplicationContext("root-context.xml");
			}
		}
		return ctx;
	}
}