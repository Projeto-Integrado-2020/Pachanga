package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.UsuarioBuilder;
import com.eventmanager.pachanga.builder.UsuarioTOBuilder;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;

public class UsuarioFactory {
	
	private UsuarioFactory() {
	}
	
	public static Usuario getUsuario(UsuarioTO userto) {
		return UsuarioBuilder.getInstance().codUsuario(userto.getCodUsuario()).dtNasc(userto.getDtNasc())
				.email(userto.getEmail()).nomeUser(userto.getNomeUser()).senha(userto.getSenha())
				.sexo(userto.getSexo()).tipConta(userto.getTipConta()).build();
	}
	
	public static UsuarioTO getUsuarioTO(Usuario user) {
		return UsuarioTOBuilder.getInstance().codUsuario(user.getCodUsuario()).dtNasc(user.getDtNasc()).email(user.getEmail())
				.nomeUser(user.getNomeUser()).sexo(user.getSexo()).tipConta(user.getTipConta()).build();
	}

}
