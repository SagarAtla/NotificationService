package com.notificationspringrest.notificationspringrest.notificationservices;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;
import com.notificationspringrest.notificationspringrest.exceptions.EmailServiceException;

public class EmailServiceImpl implements EmailService {
	
	// clientId - KEY, List of Message - VALUE
	HashMap<String, List<Notification>> clientEmailsMap;
	
	HashMap<Notification, LocalDateTime> emailTimeMap;
		
	// initialize the maps
	public EmailServiceImpl() {
		clientEmailsMap = new HashMap<>();
		emailTimeMap = new HashMap<>();
	}
	
	private List<Notification> getEmailsFilteredByDate(String clientId, LocalDateTime from, LocalDateTime to) throws EmailServiceException {
		if(clientEmailsMap == null) {
			throw new EmailServiceException("Client to Email Database is Empty");
		}
		
		List<Notification> emailsFilteredByDate = new ArrayList<Notification>();
		for (Notification emailNotification : clientEmailsMap.get(clientId) ) {
			// the date time of the notification fetched is greater than or equal to FROM && less than or equal to TO
			if((emailTimeMap.get(emailNotification).compareTo(from) >= 0) && (emailTimeMap.get(emailNotification).compareTo(to) <= 0)) {
				emailsFilteredByDate.add(emailNotification);
			}
		}
		return emailsFilteredByDate;
	}
	
	private List<Notification> getEmailsFilteredByStatus(String clientId, NotificationStatus status) throws EmailServiceException {
		if(clientEmailsMap == null) {
			throw new EmailServiceException("Client to Email Database is Empty");
		}
		
		List<Notification> emailsFilteredByStatus = new ArrayList<Notification>();
		for (Notification emailNotification : clientEmailsMap.get(clientId) ) {
			// the status of the notification fetched is equal to status
			if(emailNotification.getStatus() == status) {
				emailsFilteredByStatus.add(emailNotification);
			}
		}
		return emailsFilteredByStatus;
	}

	@Override
	public void addEmailToDb(String clientId, Notification emailNotification) {
		
		// set the status of the email to success
		if(emailNotification.isToEmailAddValid()) {
			emailNotification.setStatus(NotificationStatus.SUCCESS);
		} else {
			System.out.println("To-Email Notification NOT VALID");
			emailNotification.setStatus(NotificationStatus.FAILURE);
		}
		
		if(clientEmailsMap.containsKey(clientId)) {
			// add the current notification against the user
			clientEmailsMap.get(clientId).add(emailNotification);
			
			// update the time map
			emailTimeMap.put(emailNotification, LocalDateTime.now());
			return;
		}
		
		// create a new email list and add to hashMap
		List<Notification> emailsList = new ArrayList<Notification>();
		
		// add the first notification to the email list
		emailsList.add(emailNotification);
		emailTimeMap.put(emailNotification, LocalDateTime.now());
		clientEmailsMap.put(clientId, emailsList);
	}

	@Override
	public List<Notification> getEmails(String clientId) throws EmailServiceException {
		if(clientEmailsMap == null) {
			throw new EmailServiceException("Client to Email Database is Empty");
		}
		return clientEmailsMap.get(clientId);
	}

	@Override
	public List<Notification> getEmails(String clientId, LocalDateTime from, LocalDateTime to) throws EmailServiceException {
		return getEmailsFilteredByDate(clientId, from, to);
	}

	@Override
	public List<Notification> getEmails(String clientId, NotificationStatus status) throws EmailServiceException {
		return getEmailsFilteredByStatus(clientId, status);
	}

}