package com.bank.model;

public class Transaction {

	private String date;
	private String type;
	private String amount;
	
	public Transaction(String date, String type, String amount) {
		super();
		this.date = date;
		this.type = type;
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [date=" + date + ", type=" + type + ", amount=" + amount + "]";
	}
	
	
	
}
