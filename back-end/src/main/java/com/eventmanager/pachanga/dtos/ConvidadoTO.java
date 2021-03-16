package com.eventmanager.pachanga.dtos;

public class ConvidadoTO {
	
	private int codConvidado;
	private String email;
	private String statusConvite;
	
	public int getCodConvidado() {
		return codConvidado;
	}
	public void setCodConvidado(int codConvidado) {
		this.codConvidado = codConvidado;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatusConvite() {
		return statusConvite;
	}
	public void setStatusConvite(String statusConvite) {
		this.statusConvite = statusConvite;
	}
}
