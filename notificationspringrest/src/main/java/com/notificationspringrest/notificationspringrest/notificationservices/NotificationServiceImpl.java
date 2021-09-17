package com.notificationspringrest.notificationspringrest.notificationservices;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationType;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;
import com.notificationspringrest.notificationspringrest.exceptions.EmailServiceException;
import com.notificationspringrest.notificationspringrest.exceptions.ClientIdNullException;
import com.notificationspringrest.notificationspringrest.exceptions.InputListNullException;
import com.notificationspringrest.notificationspringrest.exceptions.QueryNotificationException;

//keep this annotation to work the auto wired in the controller class
@Service
public class NotificationServiceImpl implements NotificationService {
	// logger instance
	private static final Logger LOGGER=LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	private MessageService msgService;
	private EmailService emailService;
	
	// initialize the objects
	public NotificationServiceImpl() {
		msgService = new MessageServiceImpl();
		emailService = new EmailServiceImpl();
	}

	@Override
	public List<Notification> getNotifications(String clientId, LocalDateTime from, LocalDateTime to, NotificationStatus status) throws QueryNotificationException, EmailServiceException {
		
		LOGGER.info("entry for getNotifications with values: {},{},{},{}", clientId, from, to, status);
		List<Notification> listOfNotifications = new ArrayList<>();
		if(from==null && to==null && status==null) {
			// wants all messages and email
			listOfNotifications.addAll(msgService.getMessages(clientId));
			listOfNotifications.addAll(emailService.getEmails(clientId));
		} else if(from!=null && to!=null && status==null) {
			// wants all messages between a time frame
			listOfNotifications.addAll(msgService.getMessages(clientId, from, to));
			listOfNotifications.addAll(emailService.getEmails(clientId, from, to));
		} else if(from==null && to==null && status!=null) {
			listOfNotifications.addAll(msgService.getMessages(clientId, status));
			listOfNotifications.addAll(emailService.getEmails(clientId, status));
		} else {
			throw new QueryNotificationException("Wrong query notification request");
		}
		
		return listOfNotifications;
	}
	
	@Override
	public void addNotificationsToDb(String clientId, List<Notification> allNotifications) throws ClientIdNullException, InputListNullException {
		if(clientId == null) {
			throw new ClientIdNullException("Please provide a valid Client ID");
		}
		
		if(allNotifications == null) {
			throw new InputListNullException("Please provide a valid List of Notifications");
		}
		// maintain two separate lists for storing mails and messages
		for(Notification notification : allNotifications) {
			if(notification.getType() == NotificationType.MESSAGE) {
				msgService.addMessageToDb(clientId, notification);
			} else if(notification.getType() == NotificationType.MAIL) {
				emailService.addEmailToDb(clientId, notification);
			}
		}

	}

}

