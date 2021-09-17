package com.notificationspringrest.notificationspringrest.notificationservices;

import java.time.LocalDateTime;
import java.util.List;

import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;
import com.notificationspringrest.notificationspringrest.exceptions.EmailServiceException;

public interface EmailService {
	public void addEmailToDb(String clientId, Notification email);
	public List<Notification> getEmails(String clientId) throws EmailServiceException;
	public List<Notification> getEmails(String clientId, LocalDateTime from, LocalDateTime to) throws EmailServiceException;
	public List<Notification> getEmails(String clientId, NotificationStatus status) throws EmailServiceException;
}
