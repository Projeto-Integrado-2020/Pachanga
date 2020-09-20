package com.eventmanager.pachanga.securingweb;

import java.io.Serializable;
import java.util.Date;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final Date jwtTimeExp;

	public JwtResponse(String jwttoken, Date jwtTime) {
		this.jwttoken = jwttoken;
		this.jwtTimeExp = jwtTime;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
	public Date getTimeToken() {
		return this.jwtTimeExp;
	}
}