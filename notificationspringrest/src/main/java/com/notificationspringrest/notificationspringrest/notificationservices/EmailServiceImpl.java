package com.notificationspringrest.notificationspringrest.notificationservices;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.notificationspringrest.notificationspringrest.entities.Attachment;
import com.notificationspringrest.notificationspringrest.entities.Mail;
import com.notificationspringrest.notificationspringrest.entities.Notification;
import com.notificationspringrest.notificationspringrest.entities.NotificationStatus;
import com.notificationspringrest.notificationspringrest.exceptions.EmailServiceException;

public class EmailServiceImpl implements EmailService {
	
	// clientId - KEY, List of Message - VALUE
	HashMap<String, List<Mail>> clientEmailsMap;
	
	HashMap<Notification, LocalDateTime> emailTimeMap;
		
	// initialize the maps
	public EmailServiceImpl() {
		clientEmailsMap = new HashMap<>();
		emailTimeMap = new HashMap<>();
	}
	
	private List<Mail> getEmailsFilteredByDate(String clientId, LocalDateTime from, LocalDateTime to) throws EmailServiceException {
		if(clientEmailsMap == null) {
			throw new EmailServiceException("Client to Email Database is Empty");
		}
		
		List<Mail> emailsFilteredByDate = new ArrayList<>();
		for (Mail emailNotification : clientEmailsMap.get(clientId) ) {
			// the date time of the notification fetched is greater than or equal to FROM && less than or equal to TO
			if((emailTimeMap.get(emailNotification).compareTo(from) >= 0) && (emailTimeMap.get(emailNotification).compareTo(to) <= 0)) {
				emailsFilteredByDate.add(emailNotification);
			}
		}
		return emailsFilteredByDate;
	}
	
	private List<Mail> getEmailsFilteredByStatus(String clientId, NotificationStatus status) throws EmailServiceException {
		if(clientEmailsMap == null) {
			throw new EmailServiceException("Client to Email Database is Empty");
		}
		
		List<Mail> emailsFilteredByStatus = new ArrayList<>();
		for (Mail emailNotification : clientEmailsMap.get(clientId) ) {
			// the status of the notification fetched is equal to status
			if(emailNotification.getStatus() == status) {
				emailsFilteredByStatus.add(emailNotification);
			}
		}
		return emailsFilteredByStatus;
	}
	
	private void sendmail(Mail mail) throws AddressException, MessagingException, IOException {
		
		// getting the system properties
		Properties props = System.getProperties();
		
		// set the host
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		
		// get the session object
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mail.getFromEmailId(), "*****");
			}
		});
		
		session.setDebug(true);
		
		// create the MimeMessage object
		MimeMessage msg = new MimeMessage(session);
		
		try {
			// set the from address
			msg.setFrom(mail.getFromEmailId());
			
			// add recipient to message
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getToEmailId()));
			
			// add subject
			msg.setSubject(mail.getSubject());
			
			Multipart multipart = new MimeMultipart();
			// 1. append text
			// 2. append file
			
			// 1-> create a body part for text
			MimeBodyPart messagePart = new MimeBodyPart();
			try {
				// set text of message
				messagePart.setText(mail.getMailBody());
				multipart.addBodyPart(messagePart);
				
				// adding multiple attachments
				for(Attachment attachment : mail.getAttachments()) {
					// 2 -> create a body part for attachments
					MimeBodyPart attachPart = new MimeBodyPart();
					attachPart.attachFile(new File(attachment.getAttachmentLink()));
					multipart.addBodyPart(attachPart);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// set the multipart
			msg.setContent(multipart);
			
			// send the mail
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void addEmailToDb(String clientId, Mail mail) throws AddressException, MessagingException, IOException {
		
		// set the status of the email to success
		if(!mail.isToEmailAddValid()) {
			System.out.println("To Address is not valid");
		}
 
		sendmail(mail);
		
		// set the status = SUCCESS if the email is sent successfully
		mail.setStatus(NotificationStatus.SUCCESS);
		
		// update the time map
		emailTimeMap.put(mail, LocalDateTime.now());
		
		if(clientEmailsMap.containsKey(clientId)) {
			// update the {client-mail} map
			clientEmailsMap.get(clientId).add(mail);
			return;
		}
		
		// create a new email list and add to hashMap
		List<Mail> emailsList = new ArrayList<>();
		// add the first notification to the email list
		emailsList.add(mail);
		
		// update the {client-mail} map
		clientEmailsMap.put(clientId, emailsList);
	}

	@Override
	public List<Mail> getEmails(String clientId) throws EmailServiceException {
		if(clientEmailsMap == null) {
			throw new EmailServiceException("Client to Email Database is Empty");
		}
		return clientEmailsMap.get(clientId);
	}

	@Override
	public List<Mail> getEmails(String clientId, LocalDateTime from, LocalDateTime to) throws EmailServiceException {
		return getEmailsFilteredByDate(clientId, from, to);
	}

	@Override
	public List<Mail> getEmails(String clientId, NotificationStatus status) throws EmailServiceException {
		return getEmailsFilteredByStatus(clientId, status);
	}

}