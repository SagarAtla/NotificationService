package com.notificationspringrest.notificationspringrest.entities;

import java.util.regex.Pattern;

public class Notification {
	// this type is the self.type of notification the client wants to send
	private NotificationType type;
	
	private String notificationBody;
	
	private String toEmailId;
	
	private String fromEmailId;
	
	private String fromPhoneNum;
	
	private String toPhoneNum;
	
	private NotificationStatus status;
	
	private static Pattern phoneNumPattern = Pattern.compile("[0-9]+");
	private static Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");

	// method to check if the notification has valid "from" phone number
	public boolean isFromMsgNumValid(Notification this) {
		if(this.getFromPhoneNum() != null) {
			return phoneNumPattern.matcher(this.getFromPhoneNum()).matches() && this.getFromPhoneNum().length() == 10;
		}
		System.out.println("Client Phone Number is not valid");
		return false;
	}
	
	// method to check if the notification has valid "to" phone number
	public boolean isToMsgNumValid(Notification this) {
		if(this.getToPhoneNum() != null) {
			return phoneNumPattern.matcher(this.getToPhoneNum()).matches() && this.getToPhoneNum().length() == 10;
		}
		System.out.println("To Phone Number is not valid");
		return false;
	}
	
	// method to check if the notification has valid "from" mail id
	public boolean isFromEmailAddValid(Notification this) {
		if(this.getFromEmailId() != null) {
			return emailPattern.matcher(this.getFromEmailId()).matches();
		}
		System.out.println("Client From Email Address is not valid");
		return false;
	}
	
	// method to check if the notification has valid "from" mail id
	public boolean isToEmailAddValid(Notification this) {
		if(this.getToEmailId() != null) {
			return emailPattern.matcher(this.getToEmailId()).matches();
		}
		System.out.println("Client To Email Address is not valid");
		return false;
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

	public String getToEmailId() {
		return toEmailId;
	}

	public void setToEmailId(String toEmailId) {
		this.toEmailId = toEmailId;
	}

	public String getFromEmailId() {
		return fromEmailId;
	}

	public void setFromEmailId(String fromEmailId) {
		this.fromEmailId = fromEmailId;
	}

	public String getFromPhoneNum() {
		return fromPhoneNum;
	}

	public void setFromPhoneNum(String fromPhoneNum) {
		this.fromPhoneNum = fromPhoneNum;
	}

	public String getToPhoneNum() {
		return toPhoneNum;
	}

	public void setToPhoneNum(String toPhoneNum) {
		this.toPhoneNum = toPhoneNum;
	}

	public NotificationStatus getStatus() {
		return status;
	}

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}
	
}

