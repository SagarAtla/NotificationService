package com.notificationspringrest.notificationspringrest.entities;

public class Client {
	private int clientId;
	
	public Client(int clientId, String clientName) {
		super();
		this.clientId = clientId;
	}
	
	public int getClientId() {
		return clientId;
	}
	
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}