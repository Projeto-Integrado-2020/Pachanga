package com.eventmanager.pachanga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.utils.HashBuilder;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;

	public Usuario cadastro(Usuario user){
		validacaoCadastro(user);
		if("P".equals(user.getTipConta())) {
			user.setSenha(HashBuilder.gerarSenha(user.getSenha()));
		}
		user.setCodUsuario(userRepository.getNextValMySequence());
		userRepository.save(user);
		return user;
	}

	public void validacaoCadastro(Usuario user){
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente != null) {
			throw new ValidacaoException("Outra conta está usando esse e-mail");
		}
	}


	public Usuario login(Usuario user){
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(validacaoLogin(usuarioExistente, user)) {
			return usuarioExistente;
		}
		throw new ValidacaoException("Usuário ou senha incorretos");
	}

	public boolean validacaoLogin(Usuario usuarioExistente, Usuario userLogin){
		if(usuarioExistente != null) {
			if("P".equals(usuarioExistente.getTipConta())) {
				boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), usuarioExistente.getSenha());
				if(!senhasIguais) {
					throw new ValidacaoException("Usuário ou senha incorretos");
				}
			} 
			return true;
		}
		throw new ValidacaoException("Usuário não cadastrado");
	}

}
