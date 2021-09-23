package com.notificationspringrest.notificationspringrest.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notificationspringrest.notificationspringrest.entities.Mail;
// import com.springrest.springrest.entities.Client;
import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;
import com.notificationspringrest.notificationspringrest.entities.NotificationType;
import com.notificationspringrest.notificationspringrest.entities.ResponseModel;
import com.notificationspringrest.notificationspringrest.entities.Status;
import com.notificationspringrest.notificationspringrest.exceptions.QueryNotificationException;
import com.notificationspringrest.notificationspringrest.notificationservices.NotificationService;
import com.notificationspringrest.notificationspringrest.notificationservices.NotificationServiceImpl;

@RestController
public class MainNotificationService {
	// logger instance
	private static final Logger LOGGER=LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	// creates and injects the object into this
	@Autowired
	private NotificationService notifyService;
	
	@GetMapping("/home")
	public ResponseModel home() {
		ResponseModel rModel = new ResponseModel(Status.SUCCESS);
		try {
			String welcomeMsg = "Welcome to the home page of Notification Services";
			rModel.setData(welcomeMsg);
		} catch (Exception e) {
			rModel = new ResponseModel(Status.FAILED);
			rModel.setData(e.getMessage());
		}
		return rModel;
	}
	
	// post request for mail notification service
	@PostMapping("/Notifications/Mails/clientId/{clientId}")
	public ResponseModel subscribeNotifications(@PathVariable String clientId, @RequestBody List<Mail> listOfNotifications) {
		ResponseModel rModel = new ResponseModel(Status.SUCCESS);
		try {
			this.notifyService.addNotificationsToDb(clientId, listOfNotifications);
			rModel.setData(listOfNotifications);
		} catch (Exception e) {
			rModel = new ResponseModel(Status.FAILED);
			rModel.setData(e.getMessage());
			LOGGER.error("An ERROR Message");
		}
		return rModel;
	}
	
	
	// get all notifications sent by a client {messages / mails}
	@GetMapping("/Notifications/clientId/{clientId}")
	public ResponseModel getAllNotifications(@PathVariable String clientId) throws QueryNotificationException {
		ResponseModel rModel = new ResponseModel(Status.SUCCESS);
		try {
			List<Notification> notificationList = this.notifyService.getNotifications(clientId, null, null, null, NotificationType.ALL);
			rModel.setData(notificationList);
		} catch(Exception e) {
			rModel = new ResponseModel(Status.FAILED);
			rModel.setData(e.getMessage());
			LOGGER.error("An ERROR Message");
		}
		return rModel;
		
	}
	
	// get all mails filtered by date
	@GetMapping("/Notifications/Mails/clientId/{clientId}/startTime/{from}/endTime/{to}")
	public ResponseModel getAllNotifications(@PathVariable String clientId, @PathVariable String from, @PathVariable String to) throws QueryNotificationException {
		ResponseModel rModel = new ResponseModel(Status.SUCCESS);
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
			
			// convert string to date time
			LocalDateTime fromTime = LocalDateTime.parse(from, formatter);
			LocalDateTime toTime = LocalDateTime.parse(to, formatter);

			List<Notification> notificationList = this.notifyService.getNotifications(clientId, fromTime, toTime, null, NotificationType.MAIL);
			rModel.setData(notificationList);
		} catch (Exception e) {
			rModel = new ResponseModel(Status.FAILED);
			rModel.setData(e.getMessage());
			LOGGER.error("An ERROR Message");
		}
		return rModel;
		
	}
	
	// get all messages filtered by status
	@GetMapping("/Notifications/Mails/clientId/{clientId}/status/{status}")
	public ResponseModel getAllNotifications(@PathVariable String clientId, @PathVariable String status) throws QueryNotificationException {
		ResponseModel rModel = new ResponseModel(Status.SUCCESS);
		try {
			List<Notification> notificationList = this.notifyService.getNotifications(clientId, null, null, NotificationStatus.valueOf(status), NotificationType.MAIL);
			rModel.setData(notificationList);
		} catch (Exception e) {
			rModel = new ResponseModel(Status.FAILED);
			rModel.setData(e.getMessage());
			LOGGER.error("An ERROR Message");
		}
		return rModel;
	}
	
	// unimplemented APIs
	
	/*
	 * @PostMapping("/Notifications/Messages/clientId/{clientId}")
	 * @GetMapping("/Notifications/Messages/clientId/{clientId}/startTime/{from}/endTime/{to}")
	 * @GetMapping("/Notifications/Messages/clientId/{clientId}/status/{status}")
	 * 
	 * 
	*/
	
	/*
	 * @GetMapping("/Notifications/Summary")
	 * Pair<clientId, Pair<DateTime, List<Notification>>
	 */

}