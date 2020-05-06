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
		if(user.getTipConta() == "P") {
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
		usuarioExistente = userRepository.findByNomeUser(user.getNomeUser());
		if(usuarioExistente != null) {
			throw new ValidacaoException("Nome já utilizado por um outro usuário");
		}
	}

	public Usuario login(Usuario user) throws ValidacaoException{
		Usuario usuarioExistente = userRepository.findByEmail(user.getEmail());
		if(validacaoLogin(usuarioExistente, user)) {
			return usuarioExistente;
		}
		return null;
	}

	public Boolean validacaoLogin(Usuario usuarioExistente, Usuario userLogin) throws ValidacaoException{
		if(usuarioExistente != null) {
			if(usuarioExistente.getTipConta() == "P") {
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
