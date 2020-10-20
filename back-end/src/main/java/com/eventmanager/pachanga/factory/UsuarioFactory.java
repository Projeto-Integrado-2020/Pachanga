package com.eventmanager.pachanga.factory;

import java.util.ArrayList;
import java.util.List;

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
				.genero(userto.getGenero()).pronome(userto.getPronome()).build();
	}
	
	public static UsuarioTO getUsuarioTO(Usuario user) {
		return UsuarioTOBuilder.getInstance().codUsuario(user.getCodUsuario()).dtNasc(user.getDtNasc()).email(user.getEmail()).pronome(user.getPronome())
				.nomeUser(user.getNomeUser()).genero(user.getGenero()).tipConta(user.getSenha() != null ? TipoConta.PACHANGA.getDescricao() : TipoConta.GMAIL.getDescricao()).build();
	}

	public static UsuarioTO getUsuarioTO(Usuario user, String funcionalidade) {
		return UsuarioTOBuilder.getInstance().codUsuario(user.getCodUsuario()).dtNasc(user.getDtNasc()).email(user.getEmail()).pronome(user.getPronome())
				.nomeUser(user.getNomeUser()).genero(user.getGenero()).tipConta(user.getSenha() != null ? TipoConta.PACHANGA.getDescricao() : TipoConta.GMAIL.getDescricao()).funcionalidade(funcionalidade).build();
	}
	
	public static List<UsuarioTO> getUsuariosTO(List<Usuario> usuarios) {
		List<UsuarioTO> retorno = new ArrayList<>();
		for(Usuario usuario : usuarios) {
			retorno.add(getUsuarioTO(usuario));
			
		}
		return retorno;
	}

}
