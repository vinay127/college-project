package com.collegeproject.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.collegeproject.codetype.NotificationInfo;
import com.collegeproject.core.TransactionInfo;
import com.collegeproject.model.User;
import com.collegeproject.model.UserAuthenticationToken;
import com.collegeproject.service.UserAuthenticationTokenService;




@Path("/")
public class LoginResource {

	@Autowired
	UserAuthenticationTokenService userAuthenticationService;

	@Autowired
	TransactionInfo transactionInfo;

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(User user, @Context HttpServletRequest req) {
		UserAuthenticationToken authenticationToken = userAuthenticationService.createToken(user);
		if (null == authenticationToken || authenticationToken.getToken().isEmpty()) {
			transactionInfo.generateException("invalid.user", NotificationInfo.ERROR,
					Status.UNAUTHORIZED.getStatusCode());
			return Response.status(401).build();
		} else {
			return Response.status(200).entity(authenticationToken).build();
		}
	}

	@DELETE
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logOut(@Context HttpServletRequest req) {
		UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken();
		userAuthenticationToken.setToken(req.getHeader("token"));
		userAuthenticationService.deleteToken(userAuthenticationToken);
		return Response.status(200).build();
	}

}
