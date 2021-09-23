package com.notificationspringrest.notificationspringrest.entities;

import java.util.List;
import java.util.regex.Pattern;

public class Mail implements Notification {
	private NotificationType type;
	private String subject;
	private String mailBody;
	private String toEmailId;
	private String fromEmailId;
	private String cc;
	private String bcc;
	private NotificationStatus status = NotificationStatus.UNKNOWN;
	private List<Attachment> attachments;
	
	private static Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getMailBody() {
		return mailBody;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
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

	public NotificationStatus getStatus() {
		return status;
	}

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}
	
	// method to check if the notification has valid "from" mail id
	public boolean isFromEmailAddValid() {
		if(this.getFromEmailId() != null) {
			return emailPattern.matcher(this.getFromEmailId()).matches();
		}
		System.out.println("Client From Email Address is not valid");
		return false;
	}
	
	// method to check if the notification has valid "from" mail id
	public boolean isToEmailAddValid() {
		if(this.getToEmailId() != null) {
			return emailPattern.matcher(this.getToEmailId()).matches();
		}
		System.out.println("Client To Email Address is not valid");
		return false;
	}

}
