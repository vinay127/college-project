package com.collegeproject.core;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.collegeproject.codetype.NotificationInfo;
import com.collegeproject.exception.CollegeProjectInfoException;
import com.collegeproject.exception.CollegeProjectRuntimeException;

@Component
@Transactional(dontRollbackOn = CollegeProjectInfoException.class, rollbackOn = CollegeProjectRuntimeException.class)
public class TransactionInfo {

	@Autowired
	private ErrorMessageReader messagesProperties;
	// @Autowired
	// private EmailService emailService;
//	@Autowired
//	private AppDataUtil appDataUtil;

	public void generateException(String messageKey, NotificationInfo notificationinfo, int errorCode) {
		throw new CollegeProjectInfoException(messagesProperties.getProperty(messageKey), notificationinfo, errorCode);
	}

	public void generateException(String messageKey, List<Object> args, NotificationInfo notificationinfo,
			int errorCode) {
		throw new CollegeProjectInfoException(messagesProperties.getProperty(messageKey, args), notificationinfo, errorCode);
	}

	public void generateRuntimeException(String messageKey, NotificationInfo notificationinfo, int errorCode) {
		throw new CollegeProjectRuntimeException(messagesProperties.getProperty(messageKey), notificationinfo, errorCode);
	}

	public void generateRuntimeException(String messageKey, List<Object> args, NotificationInfo notificationinfo,
			int errorCode) {
		throw new CollegeProjectRuntimeException(messagesProperties.getProperty(messageKey, args), notificationinfo,
				errorCode);
	}

	// public void sendErrorMail(String stackTrace) {
	// String mailinglist = appDataUtil.getValueByKey("error.mailinglist");
	// String[] mailToList = mailinglist.split(",");
	// for (String to : mailToList) {
	// emailService.sendEmailWithText(null, to, "Error in CMT", stackTrace, null);
	// }
	//
	// }

}