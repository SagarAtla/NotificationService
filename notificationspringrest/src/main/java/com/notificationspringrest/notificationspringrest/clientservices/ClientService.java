package com.notificationspringrest.notificationspringrest.clientservices;

import java.util.List;

import com.notificationspringrest.notificationspringrest.entities.Client;
import com.notificationspringrest.notificationspringrest.notificationservices.NotificationService;

public interface ClientService {
	public List<Client> getClients();
}

// comment