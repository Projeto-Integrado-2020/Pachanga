package com.eventmanager.pachanga.services;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.utils.HashBuilder;

public class UsuarioService {

	public Boolean cadastro(Usuario user, UsuarioRepository repository) {
		Usuario usuarioExistente = repository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente == null) {
			if(user.getSenha() != null) {
				user.setSenha(HashBuilder.gerarSenha(user.getSenha()));
			}
			repository.save(user);
			return true;
		}
		return false;
	}

	public Usuario login(Usuario user, UsuarioRepository repository){
		Usuario usuarioExistente = repository.findByEmail(user.getEmail());
		if(validacaoLogin(usuarioExistente, user)) {
			return usuarioExistente;
		}
		return null;
	}
	
	public Boolean validacaoLogin(Usuario userExistente, Usuario userLogin) {
		if(userExistente != null) {
			if(userExistente.getTipConta().equals("P")) {
				Boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), userExistente.getSenha());
				if(senhasIguais) {
					return true;
				}
			}
			else {
				return true;
			}
		}
		return false;
	}

}
