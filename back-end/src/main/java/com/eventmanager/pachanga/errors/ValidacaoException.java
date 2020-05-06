package com.eventmanager.pachanga.errors;

public class ValidacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ValidacaoException(String Message) {
		super(Message);
	}

}
