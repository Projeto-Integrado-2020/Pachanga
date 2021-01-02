package com.eventmanager.pachanga.utils;

import java.util.ArrayList;
import java.util.List;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Usuario;

public class EnvioEmMassaDeConvite {
	private List<EnvioDeConvite> convites;
	
	public EnvioEmMassaDeConvite() {
		convites = new ArrayList<>();
	}
	
	public void enviarTudo() {
		for(EnvioDeConvite convite : convites) {
			EmailMensagem.enviarEmailQRCode(convite.getUsuario().getEmail(),
											convite.getFesta(),
											convite.getIngressos());
		}
		convites = new ArrayList<>();
	}
	
	public void upsertAll(List<Ingresso> ingressos) {
		for(Ingresso ingresso : ingressos) {
			upsertConvite(ingresso);
		}
	}
	
	public void upsertConvite(Ingresso ingresso) {
		EnvioDeConvite convite = findConviteExistente(ingresso.getFesta(), ingresso.getUsuario());
		if(convite == null) {
			EnvioDeConvite novoConvite = new EnvioDeConvite();
			novoConvite.setFesta(ingresso.getFesta());
			novoConvite.setUsuario(ingresso.getUsuario());
			novoConvite.addIngresso(ingresso);
			convites.add(novoConvite);
		} else{
			convite.addIngresso(ingresso);
		}
	}
	
	private EnvioDeConvite findConviteExistente(Festa festa, Usuario usuario) {
		for(EnvioDeConvite convite : convites) {
			if(convite.getFesta().getCodFesta() == festa.getCodFesta()
			&& convite.getUsuario().getCodUsuario() == usuario.getCodUsuario()) 
			{
				return convite;
			}
		}
		return null;
	}
}
