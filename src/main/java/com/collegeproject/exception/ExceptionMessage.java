package com.collegeproject.exception;

import org.springframework.stereotype.Component;

import com.collegeproject.codetype.NotificationInfo;

@Component
public class ExceptionMessage {

	private String errorMessage;
	private int errorCode;

	private NotificationInfo notificationInfo;

	private String exceptionStackTrace;

	@SuppressWarnings("unused")
	private ExceptionMessage() {

	}

	public ExceptionMessage(String errorMessage, NotificationInfo notificationinfo, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.notificationInfo = notificationinfo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public NotificationInfo getNotificationInfo() {
		return notificationInfo;
	}

	public void setNotificationInfo(NotificationInfo notificationInfo) {
		this.notificationInfo = notificationInfo;
	}

	public String getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

}
