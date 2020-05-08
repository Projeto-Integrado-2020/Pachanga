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

	public void cadastro(Usuario user) throws ValidacaoException{
		validacaoCadastro(user);
		if("P".equals(user.getTipConta())) {
			user.setSenha(HashBuilder.gerarSenha(user.getSenha()));
		}
		user.setCodUsuario(userRepository.getNextValMySequence());
		userRepository.save(user);
	}

	public void validacaoCadastro(Usuario user) throws ValidacaoException{
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente != null) {
			throw new ValidacaoException("Outra conta está usando esse e-mail");
		}
	}


	public Usuario login(Usuario user) throws Exception{
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(validacaoLogin(usuarioExistente, user)) {
			return usuarioExistente;
		}
		return null;
	}

	public boolean validacaoLogin(Usuario usuarioExistente, Usuario userLogin) throws Exception{
		if(usuarioExistente != null) {

			if("P".equals(usuarioExistente.getTipConta())) {
				Boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), usuarioExistente.getSenha());
				if(senhasIguais) {
					return true;
				}
			} else {
				return true;
			}
		}
		throw new ValidacaoException("Usuário ou senha incorretos");
	}

}
