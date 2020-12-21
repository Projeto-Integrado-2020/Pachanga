package com.eventmanager.pachanga.utils;

import java.util.ArrayList;
import java.util.List;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.domains.Ingresso;

public class EnvioDeConvite {
	private Festa festa;
	private Usuario usuario;
	private List<Ingresso> ingressos;
	
	public EnvioDeConvite() {
		ingressos = new ArrayList<>();
	}
	
	public Festa getFesta() {
		return festa;
	}
	public void setFesta(Festa festa) {
		this.festa = festa;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void addIngresso(Ingresso ingresso) {
		ingressos.add(ingresso);
	}

	public List<Ingresso> getIngressos() {
		return ingressos;
	}
}
