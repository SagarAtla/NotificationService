package com.notificationspringrest.notificationspringrest.notificationservices;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.notificationspringrest.notificationspringrest.entities.Client;
import com.notificationspringrest.notificationspringrest.entities.Mail;
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
	
	private EmailService emailService;
	
	// initialize the objects
	public NotificationServiceImpl() {
		emailService = new EmailServiceImpl();
	}
	
	// private method to return list of mails
	private List<Notification> getMails(String clientId, LocalDateTime from, LocalDateTime to, NotificationStatus status) throws QueryNotificationException, EmailServiceException {

		List<Notification> listOfNotifications = new ArrayList<>();
		if(from==null && to==null && status==null) {
			// wants all email
			listOfNotifications.addAll(emailService.getEmails(clientId));
		} else if(from!=null && to!=null && status==null) {
			// wants all mails between a time frame
			listOfNotifications.addAll(emailService.getEmails(clientId, from, to));
		} else if(from==null && to==null && status!=null) {
			listOfNotifications.addAll(emailService.getEmails(clientId, status));
		} else {
			throw new QueryNotificationException("Wrong query notification request");
		}
		
		return listOfNotifications;
	}
	
	/*
		// private method to return list of messages
		private List<Notification> getMessages(String clientId, LocalDateTime from, LocalDateTime to, NotificationStatus status) {
		}
	*/

	@Override
	public List<Notification> getNotifications(String clientId, LocalDateTime from, LocalDateTime to, NotificationStatus status, NotificationType type) throws QueryNotificationException, EmailServiceException {
		LOGGER.info("entry for getNotifications with values: {},{},{},{},{}", clientId, from, to, status, type);
		switch (type) {
		case ALL:
			List<Notification> allList = new ArrayList<>();
			allList.addAll(getMails(clientId, from, to, status));
			
			/* append all types of notifications here */
			// allList.addAll(getMessages(clientId, from, to, status));
		case MAIL:
			return getMails(clientId, from, to, status);
		case MESSAGE:
			System.out.println("NotificationServiceImpl:getNotifications -> Not Implemented yet");
			return null; /* not implemented */
		}
		return null; /* error */
	}
	
	@Override
	public void addNotificationsToDb(String clientId, List<Mail> allMails) throws ClientIdNullException, InputListNullException, AddressException, MessagingException, IOException {
		if(clientId == null) {
			throw new ClientIdNullException("Please provide a valid Client ID");
		}
		
		if(allMails == null) {
			throw new InputListNullException("Please provide a valid List of Notifications");
		}
		
		for(Mail mail: allMails) {
			// check if its a valid email from address
			if(!mail.isFromEmailAddValid()) {
				System.out.println("addNotificationsToDb: Should not reach this branch");
			}
			emailService.addEmailToDb(clientId, mail);
		}
	}
	
//	// implement the msg service addition
//	@Override
//	public void addNotificationsToDb(String clientId, List<Message> allNotifications) throws ClientIdNullException, InputListNullException, AddressException, MessagingException, IOException {
//	}

}

