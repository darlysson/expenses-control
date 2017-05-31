package com.algaworks.expenses.model;

public enum StatusExpense {
	
	PENDING("Pending"),
	RECEIVED("Received");
	
	private String description;
	
	StatusExpense(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
