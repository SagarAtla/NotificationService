package com.notificationspringrest.notificationspringrest.notificationservices;

import java.time.LocalDateTime;
import java.util.List;

import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;
import com.notificationspringrest.notificationspringrest.exceptions.EmailServiceException;
import com.notificationspringrest.notificationspringrest.exceptions.ClientIdNullException;
import com.notificationspringrest.notificationspringrest.exceptions.InputListNullException;
import com.notificationspringrest.notificationspringrest.exceptions.QueryNotificationException;

public interface NotificationService {
	public List<Notification> getNotifications(String clientId, LocalDateTime from, LocalDateTime to, NotificationStatus status) throws QueryNotificationException, EmailServiceException;
	public void addNotificationsToDb(String clientId, List<Notification> allNotifications) throws ClientIdNullException, InputListNullException;
}