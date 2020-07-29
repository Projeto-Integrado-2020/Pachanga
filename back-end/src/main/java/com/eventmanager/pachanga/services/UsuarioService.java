package com.eventmanager.pachanga.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.UsuarioFactory;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoConta;
import com.eventmanager.pachanga.tipo.TipoGrupo;
import com.eventmanager.pachanga.utils.HashBuilder;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	//cadastro_________________________________________________________________________________________________________	

	public Usuario cadastrar(UsuarioTO user){
		validarCadastro(user);
		if(TipoConta.PACHANGA.getDescricao().equals(user.getTipConta())) {
			user.setSenha(HashBuilder.gerarSenha(user.getSenha()));
		}
		Usuario usuarioExistente = userRepository.findByEmail(user.getEmail());
		Usuario usuario = UsuarioFactory.getUsuario(user);
		if(usuarioExistente == null) {
			usuario.setCodUsuario(userRepository.getNextValMySequence());
			userRepository.save(usuario);
			usuarioExistente = usuario;
		}else {
			if(TipoConta.GMAIL.getDescricao().equals(user.getTipConta())) {
				usuarioExistente.setGmail(user.getConta());
				userRepository.updateGmailUsuario(usuarioExistente.getCodUsuario(), user.getConta());
			}else if(TipoConta.FACEBOOK.getDescricao().equals(user.getTipConta())) {
				usuarioExistente.setFacebook(user.getConta());
				userRepository.updateFacebookUsuario(usuarioExistente.getCodUsuario(), user.getConta());
			}else {
				usuarioExistente.setSenha(user.getSenha());
				userRepository.save(usuarioExistente);
			}
		}
		return usuarioExistente;
	}

	public void validarCadastro(UsuarioTO user){
		Usuario usuarioExistente = userRepository.findByEmail(user.getEmail());
		boolean logandoViaPachanga = TipoConta.PACHANGA.getDescricao().equals(user.getTipConta());
		if((user.getSenha() == null || user.getSenha().isEmpty()) && logandoViaPachanga) {
			throw new ValidacaoException("USERSSE");//usuário sem senha
		}
		if(usuarioExistente != null) {
			if(logandoViaPachanga && usuarioExistente.getSenha() != null) {
				throw new ValidacaoException("USERMAIL");
			}else if(!logandoViaPachanga) {
				validarCadastroMeiosExternos(usuarioExistente, user);
			}
		}
	}

	public void validarCadastroMeiosExternos(Usuario usuarioExistente, UsuarioTO usuarioCadastro) {
		boolean tipoGmailCadastro = TipoConta.GMAIL.getDescricao().equals(usuarioCadastro.getTipConta());
		boolean tipoFacebookCadastro = TipoConta.FACEBOOK.getDescricao().equals(usuarioCadastro.getTipConta());
		boolean valorFaceDiferente = usuarioExistente.getFacebook() != null && !usuarioExistente.getFacebook().equals(usuarioCadastro.getConta());
		boolean valorGmailDiferente = usuarioExistente.getGmail() != null && !usuarioExistente.getGmail().equals(usuarioCadastro.getConta());
		if(usuarioCadastro.getConta() == null) {
			throw new ValidacaoException("USERNCON");// não foi passado o valor da conta para o usuário 
		}
		if(tipoGmailCadastro && valorGmailDiferente) {
			throw new ValidacaoException("USERNGMA");
		}
		if(tipoFacebookCadastro && valorFaceDiferente) {
			throw new ValidacaoException("USERNFAC");
		}

	}


	//login_________________________________________________________________________________________________________		

	public Usuario logar(UsuarioTO user){
		Usuario usuarioExistente = userRepository.findByEmail(user.getEmail());
		if(usuarioExistente == null && (TipoConta.GMAIL.getDescricao().equals(user.getTipConta()) || TipoConta.FACEBOOK.getDescricao().equals(user.getTipConta()))) {
			return cadastrar(user);
		}
		validarLogin(usuarioExistente, user);
		return usuarioExistente;
	}

	public void validarLogin(Usuario usuarioExistente, UsuarioTO userLogin){
		boolean tipoContaPachanga = TipoConta.PACHANGA.getDescricao().equals(userLogin.getTipConta());
		if(usuarioExistente != null && tipoContaPachanga) {
			boolean senhasIguais = HashBuilder.compararSenha(userLogin.getSenha(), usuarioExistente.getSenha());
			if(!senhasIguais) {
				throw new ValidacaoException("PASSINC1");
			}
		}else if(usuarioExistente == null && tipoContaPachanga) {
			throw new ValidacaoException("EMALINCO");
		}
	}

	//atualizar_________________________________________________________________________________________________________		

	public Usuario atualizar(UsuarioTO user){
		Usuario usuarioValidado = validacaoAtualizar(user);
		usuarioValidado.setDtNasc(user.getDtNasc());
		usuarioValidado.setSexo(user.getSexo());
		usuarioValidado.setNomeUser(user.getNomeUser());

		userRepository.save(usuarioValidado);
		return usuarioValidado;
	}

	private Usuario validacaoAtualizar(UsuarioTO user) {
		Usuario usuarioBanco = userRepository.findByEmail(user.getEmail());
		if(usuarioBanco == null) {
			throw new ValidacaoException("USERNFOU");
		}
		if(TipoConta.PACHANGA.getDescricao().equals(user.getTipConta()) && user.getSenha() != null) {
			if(!HashBuilder.compararSenha(user.getSenha(), usuarioBanco.getSenha())) {
				throw new ValidacaoException("PASSINC2");
			}
			if(user.getSenhaNova() != null) {
				if(HashBuilder.compararSenha(user.getSenhaNova(), usuarioBanco.getSenha())) {
					throw new ValidacaoException("PASSDIFF");
				}
				usuarioBanco.setSenha(HashBuilder.gerarSenha(user.getSenhaNova()));
			}
			if(user.getEmailNovo() != null) {
				user.setEmail(user.getEmailNovo());
				Usuario usuarioExistente = userRepository.findByEmail(user.getEmail());
				if(usuarioExistente != null) {
					throw new ValidacaoException("USERMAIL");
				}
				usuarioBanco.setEmail(user.getEmailNovo());
			}
		}
		return usuarioBanco;
	}

	public List<Usuario> getUsuariosFesta(int codFesta){
		return userRepository.findByIdFesta(codFesta);
	}

	public String funcionalidadeUsuarioFesta(int codFesta, int codUsuario) {
		Usuario usuario = userRepository.findById(codUsuario);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		return grupoRepository.findFuncionalidade(codFesta, codUsuario);
	}

	public Usuario getUsuarioResponsavelFesta(int codFesta) {
		return userRepository.findByFestaGrupo(codFesta, TipoGrupo.ORGANIZADOR.getValor());
	}


}
