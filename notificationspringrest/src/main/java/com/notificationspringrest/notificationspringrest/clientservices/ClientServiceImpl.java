package com.notificationspringrest.notificationspringrest.clientservices;

import java.util.ArrayList;
import java.util.List;

import com.notificationspringrest.notificationspringrest.entities.Client;
import com.notificationspringrest.notificationspringrest.notificationservices.NotificationService;

public class ClientServiceImpl implements ClientService {
	
	private List<Client> clientList;

	public ClientServiceImpl() {
		clientList = new ArrayList<>();
		/*
			we can initialize clients here
			or create a registration service
		*/
	}

	@Override
	public List<Client> getClients() {
		return clientList;
	}

}

// comment