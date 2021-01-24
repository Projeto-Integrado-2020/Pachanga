package com.eventmanager.pachanga.dtos;

import java.util.List;

public class PDFPorEmailTO {
	private List<String> listaDeEmails;
	private String base64Pdf;
	
	
	
	public List<String> getListaDeEmails() {
		return listaDeEmails;
	}
	public void setListaDeEmails(List<String> listaDeEmails) {
		this.listaDeEmails = listaDeEmails;
	}
	public String getBase64Pdf() {
		return base64Pdf;
	}
	public void setBase64Pdf(String base64Pdf) {
		this.base64Pdf = base64Pdf;
	}
	
	
}
