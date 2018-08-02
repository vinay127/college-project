package com.collegeproject.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.collegeproject.codetype.NotificationInfo;


@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
	private static final Logger logger = LoggerFactory.getLogger(GenericExceptionMapper.class);

	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		logger.error(exception.fillInStackTrace().toString());
		ExceptionMessage errorMessage = new ExceptionMessage("Oops Something went wrong. Please contact your administrator",
				NotificationInfo.ERROR, 500);
		errorMessage.setExceptionStackTrace(exception.fillInStackTrace().toString());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).type(MediaType.APPLICATION_JSON)
				.build();
	}

}
