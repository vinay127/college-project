package com.collegeproject.core;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:errormessages.properties")
public class ErrorMessageReader {

	@Autowired
	private Environment env;

	public String getProperty(String property) {
		return env.getProperty(property);
	}

	public String getProperty(String property, List<Object> args) {
		String format = MessageFormat.format(getProperty(property), args.toArray());
		return format;
	}
}