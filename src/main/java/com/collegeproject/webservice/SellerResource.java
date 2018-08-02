package com.collegeproject.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.collegeproject.core.TransactionInfo;
import com.collegeproject.model.Seller;
import com.collegeproject.model.UserAuthenticationToken;
import com.collegeproject.service.SellerService;
import com.collegeproject.service.UserAuthenticationTokenService;

@Path("/sellers")
public class SellerResource {

	@Context
	private javax.servlet.http.HttpServletRequest req;

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	UserAuthenticationTokenService authenticationTokenService;

//	@Autowired
//	ProductService productService;

	@Autowired
	TransactionInfo transactionInfo;

	@GET
	@Path("/{catererid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("catererid") long catererid) {
		return Response.ok().entity(sellerService.readById(catererid)).build();
	}

	@GET
	@Path("/active")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllActive() {
		return Response.ok().entity(sellerService.readAllInActiveSellers()).build();
	}

	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByName(@PathParam("name") String name) {
		return Response.ok().entity(sellerService.readActiveByName(name)).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCaterer(Seller seller) {
		return Response.ok().entity(sellerService.validateAndCreate(seller)).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCaterer(Seller seller) {
		return Response.ok().entity(sellerService.update(seller)).build();
	}

	private com.collegeproject.model.User getUser() {
		String token = req.getHeader("token");
		UserAuthenticationToken authToken = authenticationTokenService.findByToken(token);
		if (null != authToken)
			return authToken.getUser();
		return null;

	}

//	@GET
//	@Path("/projects")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getAllProducts() {
//		return Response.ok().entity(productService.readAllActiveByCaterer(catererService.getByUser(getUser()))).build();
//	}

//	@GET
//	@Path("/{name}/products")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getAllProductsOfCaterer(@PathParam("name") String name) {
//		Caterer caterer = catererService.readActiveByName(name);
//		if (null == caterer)
//			transactionInfo.generateRuntimeException("NO_CATERER", NotificationInfo.ERROR,
//					Status.INTERNAL_SERVER_ERROR.getStatusCode());
//		return Response.ok().entity(productService.readAllActiveByCaterer(caterer)).build();
//	}


}
