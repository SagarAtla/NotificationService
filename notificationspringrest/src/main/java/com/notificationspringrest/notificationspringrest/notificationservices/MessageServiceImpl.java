package com.notificationspringrest.notificationspringrest.notificationservices;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;

public class MessageServiceImpl implements MessageService {
	
	// clientId - KEY, List of Message - VALUE
	HashMap<String, List<Notification>> clientMessagesMap;
	
	HashMap<Notification, LocalDateTime> msgTimeMap;
	
	// initialize the maps
	public MessageServiceImpl() {
		clientMessagesMap = new HashMap<>();
		msgTimeMap = new HashMap<>();
	}
	
	private List<Notification> getMessagesFilteredByDate(String clientId, LocalDateTime from, LocalDateTime to) {
		List<Notification> msgsFilteredByDate = new ArrayList<Notification>();
		for (Notification msgNotification : clientMessagesMap.get(clientId) ) {
			// the date time of the notification fetched is greater than or equal to FROM && less than or equal to TO
			if((msgTimeMap.get(msgNotification).compareTo(from) >= 0) && (msgTimeMap.get(msgNotification).compareTo(to) <= 0)) {
				msgsFilteredByDate.add(msgNotification);
			}
		}
		return msgsFilteredByDate;
	}
	
	private List<Notification> getMessagesFilteredByStatus(String clientId, NotificationStatus status) {
		List<Notification> msgsFilteredByStatus = new ArrayList<Notification>();
		for (Notification msgNotification : clientMessagesMap.get(clientId) ) {
			// the status of the notification fetched is equal to status
			if(msgNotification.getStatus() == status) {
				msgsFilteredByStatus.add(msgNotification);
			}
		}
		return msgsFilteredByStatus;
	}

	@Override
	public void addMessageToDb(String clientId, Notification message) {
		
		// set the status of the email to success
		if(message.isToMsgNumValid()) {
			message.setStatus(NotificationStatus.SUCCESS);
		} else {
			message.setStatus(NotificationStatus.FAILURE);
		}
				
		if(clientMessagesMap.containsKey(clientId)) {
			// add the current notification in the map against the user
			clientMessagesMap.get(clientId).add(message);
			
			// update the time map
			msgTimeMap.put(message, LocalDateTime.now());
			return;
		}
		
		// create a new message list and add to hashMap
		List<Notification> messageList = new ArrayList<Notification>();
		
		// add the first message to the notification list
		messageList.add(message);
		msgTimeMap.put(message, LocalDateTime.now());
		clientMessagesMap.put(clientId, messageList);
	}

	@Override
	public List<Notification> getMessages(String clientId) {
		System.out.println("getMessages " + clientId);
		return clientMessagesMap.get(clientId);
	}

	@Override
	public List<Notification> getMessages(String clientId, LocalDateTime from, LocalDateTime to) {
		return getMessagesFilteredByDate(clientId, from, to);
	}

	@Override
	public List<Notification> getMessages(String clientId, NotificationStatus status) {
		return getMessagesFilteredByStatus(clientId, status);
	}
	
}

