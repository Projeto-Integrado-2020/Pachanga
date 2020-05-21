package com.eventmanager.pachanga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.builder.UsuarioBuilder;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.utils.HashBuilder;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		userRepository=usuarioRepository;
	}

	public Usuario cadastrar(UsuarioTO user){
		validarCadastro(user);
		if("P".equals(user.getTipConta())) {
			user.setSenha(HashBuilder.gerarSenha(user.getSenha()));
		}
		user.setCodUsuario(userRepository.getNextValMySequence());
		Usuario usuario = criacaoUsuario(user);
		userRepository.save(usuario);
		return usuario;
	}

	public void validarCadastro(UsuarioTO user){
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente != null) {
			throw new ValidacaoException("1");
		}
	}


	public Usuario logar(UsuarioTO user){
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente == null && ("G".equals(user.getTipConta()) || "F".equals(user.getTipConta()))) {
			return cadastrar(user);
		}
		if(validarLogin(usuarioExistente, user)) {
			return usuarioExistente;
		}else {
			throw new ValidacaoException("2");
		}
	}

	public boolean validarLogin(Usuario usuarioExistente, UsuarioTO userLogin){
		if(usuarioExistente != null) {
			if("P".equals(usuarioExistente.getTipConta())) {

				boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), usuarioExistente.getSenha());
				if(!senhasIguais) {
					return false;
				}
			} 
			return true;
		}
		throw new ValidacaoException("3");
	}
	
	private Usuario criacaoUsuario(UsuarioTO userto) {
		return UsuarioBuilder.getInstance().CodUsuario(userto.getCodUsuario()).DtNasc(userto.getDtNasc())
				   .Email(userto.getEmail()).NomeUser(userto.getNomeUser()).Senha(userto.getSenha())
				   .Sexo(userto.getSexo()).TipConta(userto.getTipConta()).build();
	}

}
