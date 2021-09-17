package com.notificationspringrest.notificationspringrest.entities;

public enum Status {
	FAILED (909),
	SUCCESS (200);
	int status;
	Status(int statusVal) {
		this.status = statusVal;
	}
}
