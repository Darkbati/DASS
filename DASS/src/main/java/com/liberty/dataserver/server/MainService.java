package com.liberty.dataserver.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.Assert;

import com.liberty.dataserver.config.SpringContext;

public class MainService {
	static Log log = LogFactory.getLog(MainService.class.getName());

	public static void main(String[] args) throws Exception {
		// Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
		GenericApplicationContext ctx = SpringContext.getContext();
		Assert.notNull(ctx);

		NettyServer netty = ctx.getBean(NettyServer.class);
		netty.start();
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
