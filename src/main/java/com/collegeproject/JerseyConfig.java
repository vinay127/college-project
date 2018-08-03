package com.collegeproject;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.collegeproject.exception.GenericExceptionMapper;
import com.collegeproject.webservice.Demodata;
import com.collegeproject.webservice.LoginResource;


@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		registerEndPoints();
	}

	public void registerEndPoints() {
		register(Demodata.class);
		register(GenericExceptionMapper.class);	
		register(LoginResource.class);
	}

}
