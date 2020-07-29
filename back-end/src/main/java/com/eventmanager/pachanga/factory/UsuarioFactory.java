package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.UsuarioBuilder;
import com.eventmanager.pachanga.builder.UsuarioTOBuilder;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.tipo.TipoConta;

public class UsuarioFactory {
	
	private UsuarioFactory() {
	}
	
	public static Usuario getUsuario(UsuarioTO userto) {
		UsuarioBuilder usuarioBuilder = UsuarioBuilder.getInstance();
		if(TipoConta.GMAIL.getDescricao().equals(userto.getTipConta())) {
			usuarioBuilder.gmail(userto.getConta());
		}else if(TipoConta.FACEBOOK.getDescricao().equals(userto.getTipConta())) {
			usuarioBuilder.facebook(userto.getConta());
		}
		return usuarioBuilder.codUsuario(userto.getCodUsuario()).dtNasc(userto.getDtNasc())
				.email(userto.getEmail()).nomeUser(userto.getNomeUser()).senha(userto.getSenha())
				.sexo(userto.getSexo()).build();
	}
	
	public static UsuarioTO getUsuarioTO(Usuario user) {
		return UsuarioTOBuilder.getInstance().codUsuario(user.getCodUsuario()).dtNasc(user.getDtNasc()).email(user.getEmail())
				.nomeUser(user.getNomeUser()).sexo(user.getSexo()).build();
	}

	public static UsuarioTO getUsuarioTO(Usuario user, String funcionalidade) {
		return UsuarioTOBuilder.getInstance().codUsuario(user.getCodUsuario()).dtNasc(user.getDtNasc()).email(user.getEmail())
				.nomeUser(user.getNomeUser()).sexo(user.getSexo()).funcionalidade(funcionalidade).build();
	}

}
