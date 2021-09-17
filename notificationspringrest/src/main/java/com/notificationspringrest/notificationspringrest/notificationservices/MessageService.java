package com.notificationspringrest.notificationspringrest.notificationservices;

import java.time.LocalDateTime;
import java.util.List;

import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;

public interface MessageService {
	public void addMessageToDb(String clientId, Notification message);
	public List<Notification> getMessages(String clientId);
	public List<Notification> getMessages(String clientId, LocalDateTime from, LocalDateTime to);
	public List<Notification> getMessages(String clientId, NotificationStatus status);
}
