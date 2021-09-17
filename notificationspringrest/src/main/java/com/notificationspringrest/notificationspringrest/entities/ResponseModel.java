package com.notificationspringrest.notificationspringrest.entities;

public class ResponseModel {
	Status responseCode;
	Object data;
	
	public Status getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Status responseCode) {
		this.responseCode = responseCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public ResponseModel(Status responseCode) {
		this.responseCode = responseCode;
	}
}
