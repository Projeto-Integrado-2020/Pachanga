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

	public void cadastro(Usuario user) throws Exception{
			validacaoCadastro(user);
			if(user.getTipConta() == "P") {
				user.setSenha(HashBuilder.gerarSenha(user.getSenha()));
			}
			user.setCodUsuario(userRepository.getNextValMySequence());
			userRepository.save(user);
	}

	public void validacaoCadastro(Usuario user) throws Exception{
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente != null) {
			throw new Exception("Outra conta está usando esse e-mail");
		}
		usuarioExistente = userRepository.findByNomeUser(user.getNomeUser());
		if(usuarioExistente != null) {
			throw new Exception("Nome já utilizado por um outro usuário");
		}
	}

	public Usuario login(Usuario user) throws Exception{
		Usuario usuarioExistente = userRepository.findByEmail(user.getEmail());
		if(validacaoLogin(usuarioExistente, user)) {
			return usuarioExistente;
		}
		return null;
	}

	public Boolean validacaoLogin(Usuario usuarioExistente, Usuario userLogin) throws Exception{
		if(usuarioExistente != null) {
			Boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), usuarioExistente.getSenha());
			if(senhasIguais) {
				return true;
			}
		}
		throw new Exception("Usuário ou senha incorretos");
	}

}
