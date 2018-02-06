package com.liberty.dataserver.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.liberty.dataserver.cassandra.domain.User;
import com.liberty.dataserver.service.UserService;

@Controller
@Path("/test")
public class DataController {
	Logger logger = LoggerFactory.getLogger(DataController.class);

	@Autowired
	private UserService userService;

	@GET
	@Path("userlist")
	public Response getUsers() {
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(userService.getUsers()).build();
	}

	@POST
	@Path("adduser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(@RequestBody User user) {
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(userService.addUser(user)).build();
	}
	
	/*
	public Response access(@PathParam("target_file") String target_file,
			@QueryParam(value = "artistShortCode") String artistShortCode, @QueryParam("msisdn") String msisdn,
			@QueryParam(value = "ayceusertype") String ayceusertype,
			@QueryParam(value = "tnbusertype") String tnbusertype, @Context ChannelHandlerContext ctx) {

		logger.info("channelRead : "+ ctx.channel().remoteAddress() + " connected....");
		
		return Response.ok().build();
	}
	*/
}
