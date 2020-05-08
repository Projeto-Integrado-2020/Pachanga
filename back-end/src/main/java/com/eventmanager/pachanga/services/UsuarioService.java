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

<<<<<<< HEAD
	public Usuario login(Usuario user) throws Exception{
		Usuario usuarioExistente = userRepository.findByEmail(user.getEmail());
=======
	public Usuario login(Usuario user) throws ValidacaoException{
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
>>>>>>> be8b730ae01576d0f6e680fc7095bcda8030d5f6
		if(validacaoLogin(usuarioExistente, user)) {
			return usuarioExistente;
		}
		return null;
	}

	public boolean validacaoLogin(Usuario usuarioExistente, Usuario userLogin) throws Exception{
		if(usuarioExistente != null) {
<<<<<<< HEAD
			if(usuarioExistente.getTipConta() == "P") {
				boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), usuarioExistente.getSenha());
=======
			if("P".equals(usuarioExistente.getTipConta())) {
				Boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), usuarioExistente.getSenha());
>>>>>>> be8b730ae01576d0f6e680fc7095bcda8030d5f6
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
