package com.collegeproject.exception;

import com.collegeproject.codetype.NotificationInfo;

public class CollegeProjectInfoException extends RuntimeException {

	private ExceptionMessage exceptionMessage;
	private static final long serialVersionUID = -706783001803490307L;

	public CollegeProjectInfoException(String message, NotificationInfo notificationinfo, int errorCode) {
		super(message);
		this.exceptionMessage = new ExceptionMessage(message, notificationinfo, errorCode);
	}

	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
