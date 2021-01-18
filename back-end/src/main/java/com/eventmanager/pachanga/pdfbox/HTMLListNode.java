package com.eventmanager.pachanga.pdfbox;

public class HTMLListNode {

	private int orderingNumber;

	private String value;

	public int getOrderingNumber() {
		return orderingNumber;
	}



	public String getValue() {
		return value;
	}



	public HTMLListNode(int orderingNumber, String value) {
		this.orderingNumber = orderingNumber;
		this.value = value;
	}

}
