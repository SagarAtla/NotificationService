package com.notificationspringrest.notificationspringrest.notificationservices;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.notificationspringrest.notificationspringrest.entities.Mail;
import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;
import com.notificationspringrest.notificationspringrest.entities.NotificationType;
import com.notificationspringrest.notificationspringrest.exceptions.EmailServiceException;
import com.notificationspringrest.notificationspringrest.exceptions.ClientIdNullException;
import com.notificationspringrest.notificationspringrest.exceptions.InputListNullException;
import com.notificationspringrest.notificationspringrest.exceptions.QueryNotificationException;

public interface NotificationService {
	public List<Notification> getNotifications(String clientId, LocalDateTime from, LocalDateTime to, NotificationStatus status, NotificationType type) throws QueryNotificationException, EmailServiceException;
	public void addNotificationsToDb(String clientId, List<Mail> allMails) throws ClientIdNullException, InputListNullException, AddressException, MessagingException, IOException;
	// public void addNotificationsToDb(String clientId, List<Message> allMails) throws ClientIdNullException, InputListNullException, AddressException, MessagingException, IOException;
}
