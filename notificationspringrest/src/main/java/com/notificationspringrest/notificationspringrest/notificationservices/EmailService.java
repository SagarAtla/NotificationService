package com.notificationspringrest.notificationspringrest.notificationservices;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.notificationspringrest.notificationspringrest.entities.Mail;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;
import com.notificationspringrest.notificationspringrest.exceptions.EmailServiceException;

public interface EmailService {
	public void addEmailToDb(String clientId, Mail email) throws AddressException, MessagingException, IOException;
	public List<Mail> getEmails(String clientId) throws EmailServiceException;
	public List<Mail> getEmails(String clientId, LocalDateTime from, LocalDateTime to) throws EmailServiceException;
	public List<Mail> getEmails(String clientId, NotificationStatus status) throws EmailServiceException;
}
