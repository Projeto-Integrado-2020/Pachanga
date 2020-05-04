package com.eventmanager.pachanga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.utils.HashBuilder;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;
	
	public Boolean cadastro(Usuario user) {
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente == null) {
			if(user.getSenha() != null) {
				user.setSenha(HashBuilder.gerarSenha(user.getSenha()));
			}
			user.setCodUsuario(userRepository.getNextValMySequence());
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public Usuario login(Usuario user){
		Usuario usuarioExistente = userRepository.findByEmail(user.getEmail());
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
