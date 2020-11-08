package com.eventmanager.pachanga.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "info_integracao")
public class InfoIntegracao {
	
	@Id
	@Column(name="cod_info")
	private int codInfo;
	
	@Column(name="ter_integrado")
	private String terceiroInt;
	
	@Column(name="token")
	private String token;
	
	@Column(name="cod_event")
	private String codEvent;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;

	public int getCodInfo() {
		return codInfo;
	}

	public void setCodInfo(int codInfo) {
		this.codInfo = codInfo;
	}

	public String getTerceiroInt() {
		return terceiroInt;
	}

	public void setTerceiroInt(String terceiroInt) {
		this.terceiroInt = terceiroInt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public String getCodEvent() {
		return codEvent;
	}

	public void setCodEvent(String codEvent) {
		this.codEvent = codEvent;
	}

}
