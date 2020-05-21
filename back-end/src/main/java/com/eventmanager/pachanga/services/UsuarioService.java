package com.eventmanager.pachanga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.builder.UsuarioBuilder;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.utils.HashBuilder;
import com.eventmanager.pachanga.utils.TipoConta;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;

//cadastro_________________________________________________________________________________________________________	
	
	public Usuario cadastrar(UsuarioTO user){
		Usuario usuarioValidado = validarCadastro(user);
		if(usuarioValidado != null) {
			return usuarioValidado;
		}
		if(TipoConta.PACHANGA.getDescricao().equals(user.getTipConta())) {
			user.setSenha(HashBuilder.gerarSenha(user.getSenha()));
		}
		user.setCodUsuario(userRepository.getNextValMySequence());
		Usuario usuario = criacaoUsuario(user);
		userRepository.save(usuario);
		return usuario;
	}

	public Usuario validarCadastro(UsuarioTO user){
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente != null && TipoConta.PACHANGA.getDescricao().equals(usuarioExistente.getTipConta())) {
			throw new ValidacaoException("1");
		}
		if(usuarioExistente != null && !"P".equals(usuarioExistente.getTipConta())) {
			return logar(user);
		}
		return null;
	}


//login_________________________________________________________________________________________________________		
	
	public Usuario logar(UsuarioTO user){
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente == null && (TipoConta.GMAIL.getDescricao().equals(user.getTipConta()) || TipoConta.FACEBOOK.getDescricao().equals(user.getTipConta()))) {
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
			if(TipoConta.PACHANGA.getDescricao().equals(usuarioExistente.getTipConta())) {

				boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), usuarioExistente.getSenha());
				if(!senhasIguais) {
					return false;
				}
			} 
			return true;
		}
		throw new ValidacaoException("3");
	}

//atualizar_________________________________________________________________________________________________________		
	
	public Usuario atualizar(UsuarioTO user){
		Usuario usuarioBanco = getUsuarioAtualizacao(user);
		
		if(TipoConta.PACHANGA.getDescricao().equals(usuarioBanco.getTipConta())) {
			usuarioBanco.setSenha(HashBuilder.gerarSenha(user.getSenha()));
		}
		usuarioBanco.setDtNasc(user.getDtNasc());
		usuarioBanco.setSexo(user.getSexo());

		userRepository.save(usuarioBanco);
		return usuarioBanco;
	}
	
	public Usuario getUsuarioAtualizacao(UsuarioTO user){
		Usuario usuarioExistente = userRepository.findByEmailAndTipConta(user.getEmail(), user.getTipConta());
		if(usuarioExistente == null) {
			throw new ValidacaoException("3");
		}
		
		return usuarioExistente;	
	}

	
//dtos_________________________________________________________________________________________________________		
	
	private Usuario criacaoUsuario(UsuarioTO userto) {
		return UsuarioBuilder.getInstance().codUsuario(userto.getCodUsuario()).dtNasc(userto.getDtNasc())
				   .email(userto.getEmail()).nomeUser(userto.getNomeUser()).senha(userto.getSenha())
				   .sexo(userto.getSexo()).tipConta(userto.getTipConta()).build();
	}

}
