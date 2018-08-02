package com.collegeproject.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class CollegeProjectExceptionMapper implements ExceptionMapper<CollegeProjectInfoException> {
	private static final Logger logger = LoggerFactory.getLogger(CollegeProjectExceptionMapper.class);

	@Override
	public Response toResponse(CollegeProjectInfoException collegeProjectInfoException) {
		logger.error(collegeProjectInfoException.fillInStackTrace().toString());
		return Response.status(collegeProjectInfoException.getExceptionMessage().getErrorCode())
				.entity(collegeProjectInfoException.getExceptionMessage()).type(MediaType.APPLICATION_JSON).build();
	}
}
