package com.eventmanager.pachanga.idclass;

import java.io.Serializable;

public class AreaSegurancaProblemaId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int problema;
	private int area;
	
	public AreaSegurancaProblemaId() {
	}

	public AreaSegurancaProblemaId(int codProblema, int codArea) {
		this.problema = codProblema;
		this.area = codArea;
	}

	public int getProblema() {
		return problema;
	}

	public void setProblema(int problema) {
		this.problema = problema;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

}
