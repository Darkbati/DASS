package com.liberty.dataserver.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liberty.dataserver.system.ShutdownHookThread;

public class MainService {
	static Log log = LogFactory.getLog(MainService.class.getName());

	public static void main(String[] args) throws Exception {		
		Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
		/*
		GenericApplicationContext ctx = SpringContext.getContext();
		Assert.notNull(ctx);

		NettyServer netty = ctx.getBean(NettyServer.class);
		netty.start();
		*/
		
		throw new Exception("a");
	}
	
	public static boolean isNumber(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
