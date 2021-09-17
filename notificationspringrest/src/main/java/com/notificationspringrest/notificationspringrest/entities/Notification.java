package com.notificationspringrest.notificationspringrest.entities;

import java.util.regex.Pattern;

public class Notification {
	public NotificationType type;
	String notificationBody;
	String emailId;
	String phoneNum;
	NotificationStatus status;
	
	static Pattern phoneNumPattern = Pattern.compile("[0-9]+");
	static Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
	
	public Notification(NotificationType type, String notificationBody, String emailId, String phoneNum) {
		super();
		this.type = type;
		this.notificationBody = notificationBody;
		this.emailId = emailId;
		this.phoneNum = phoneNum;
		this.status = status;
	}

	public NotificationStatus getStatus() {
		return status;
	}

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}

	public NotificationType getType() {
		return type;
	}
	
	public void setType(NotificationType type) {
		this.type = type;
	}
	
	public String getNotificationBody() {
		return notificationBody;
	}
	
	public void setNotificationBody(String notificationBody) {
		this.notificationBody = notificationBody;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	// method to check if the email has valid emailId or not
	public boolean isValidEmailNotification(Notification this) {
		if(this.getEmailId() != null) {
			return emailPattern.matcher(this.getEmailId()).matches();
		}
		return false;
	}
	
	// method to check if the email has valid emailId or not
	public boolean isValidMsgNotification(Notification this) {
		if(this.getPhoneNum() != null) {
			return phoneNumPattern.matcher(this.getPhoneNum()).matches() && this.getPhoneNum().length() == 10;
		}
		return false;
	}
	
}

