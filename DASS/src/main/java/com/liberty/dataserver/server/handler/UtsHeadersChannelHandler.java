package com.liberty.dataserver.server.handler;

import org.jboss.resteasy.plugins.server.netty.NettyHttpRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class UtsHeadersChannelHandler extends SimpleChannelInboundHandler<NettyHttpRequest> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyHttpRequest msg) throws Exception {
		msg.getResponse().getOutputHeaders().add("Access-Control-Allow-Origin", "*");
		msg.getResponse().getOutputHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
		msg.getResponse().getOutputHeaders().add("Access-Control-Allow-Headers",
				"X-Requested-With, Content-Type, Content-Length");

		ctx.fireChannelRead(msg);
	}

}
