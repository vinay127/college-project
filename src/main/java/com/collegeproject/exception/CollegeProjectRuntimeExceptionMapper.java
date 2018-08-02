package com.collegeproject.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class CollegeProjectRuntimeExceptionMapper implements ExceptionMapper<CollegeProjectRuntimeException> {
	private static final Logger logger = LoggerFactory.getLogger(CollegeProjectRuntimeExceptionMapper.class);

	@Override
	public Response toResponse(CollegeProjectRuntimeException collegeProjectRuntimeException) {
		logger.error(collegeProjectRuntimeException.fillInStackTrace().toString());
		return Response.status(collegeProjectRuntimeException.getExceptionMessage().getErrorCode())
				.entity(collegeProjectRuntimeException.getExceptionMessage()).type(MediaType.APPLICATION_JSON).build();
	}
}
