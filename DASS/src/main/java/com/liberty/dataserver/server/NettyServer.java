package com.liberty.dataserver.server;

import java.util.Collection;

import javax.annotation.PreDestroy;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.ResteasyDeployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

@Component
public class NettyServer {

	@Autowired
	private ApplicationContext context;

	protected String rootResourcePath = "/";
	protected int port = 9500;
	protected int baklog = 128;
	protected int maxRequestSize = 10485760;
	protected int executorThreadCount = Runtime.getRuntime().availableProcessors() * 2;
	protected ServiceNettyJaxrsServer netty;

	public void start() {
		ResteasyDeployment dp = new ResteasyDeployment();
		Collection<Object> providers = context.getBeansWithAnnotation(Provider.class).values();
		Collection<Object> controllers = context.getBeansWithAnnotation(Controller.class).values();

		Assert.notEmpty(controllers);

		// extract providers
		if (providers != null) {
			dp.getProviders().addAll(providers);
		}
		// extract only controller annotated beans
		dp.getResources().addAll(controllers);

		netty = new ServiceNettyJaxrsServer();
		netty.setDeployment(dp);
		netty.setPort(port);
		netty.setMaxRequestSize(maxRequestSize);
		netty.setExecutorThreadCount(executorThreadCount);
		netty.setRootResourcePath(rootResourcePath);
		netty.setSecurityDomain(null);
		netty.start();
	}

	@PreDestroy
	public void cleanUp() {
		netty.stop();
	}

	public String getRootResourcePath() {
		return rootResourcePath;
	}

	public int getPort() {
		return port;
	}

}
