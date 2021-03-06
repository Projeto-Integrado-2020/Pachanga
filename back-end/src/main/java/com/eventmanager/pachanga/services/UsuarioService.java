package com.eventmanager.pachanga.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioFestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.UsuarioFactory;
import com.eventmanager.pachanga.factory.UsuarioFestaTOFactory;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoConta;
import com.eventmanager.pachanga.utils.HashBuilder;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private FestaService festaService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private UsuarioFestaTOFactory usuarioFestaTOFactory;
	
	//cadastro_________________________________________________________________________________________________________	

	public Usuario cadastrar(UsuarioTO user){
		validarCadastro(user);
		if(TipoConta.PACHANGA.getDescricao().equals(user.getTipConta())) {
			user.setSenha(HashBuilder.gerarCodigoHasheado(user.getSenha()));
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
				usuarioExistente.setGenero(user.getGenero());
				usuarioExistente.setDtNasc(user.getDtNasc());
				usuarioExistente.setNomeUser(user.getNomeUser());
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
		}else if(usuarioExistente == null && TipoConta.PACHANGA.getDescricao().equals(user.getTipConta())) {
			throw new ValidacaoException("EMALINCO");
		}else if(usuarioExistente != null && (usuarioExistente.getSenha() == null || usuarioExistente.getSenha().isEmpty()) && TipoConta.PACHANGA.getDescricao().equals(user.getTipConta()) ) {
			throw new ValidacaoException("PASSINC1");
		}
		validarLogin(usuarioExistente, user);
		return usuarioExistente;
	}

	public void validarLogin(Usuario usuarioExistente, UsuarioTO userLogin){
		boolean tipoContaPachanga = TipoConta.PACHANGA.getDescricao().equals(userLogin.getTipConta());
		boolean tipoContaGmail = TipoConta.GMAIL.getDescricao().equals(userLogin.getTipConta());
		boolean tipoFacebookGmail = TipoConta.FACEBOOK.getDescricao().equals(userLogin.getTipConta());
		boolean valorFaceDiferente = usuarioExistente.getFacebook() != null && !usuarioExistente.getFacebook().equals(userLogin.getConta());
		boolean valorGmailDiferente = usuarioExistente.getGmail() != null && !usuarioExistente.getGmail().equals(userLogin.getConta());
		if(tipoContaPachanga) {
			boolean senhasIguais = HashBuilder.compararCodigos(userLogin.getSenha(), usuarioExistente.getSenha());
			if(!senhasIguais) {
				throw new ValidacaoException("PASSINC1");
			}
		} else if(tipoContaGmail && valorGmailDiferente) {
			throw new ValidacaoException("USERNGMA");
		} else if(tipoFacebookGmail && valorFaceDiferente) {
			throw new ValidacaoException("USERNFAC");
		}
	}

	//atualizar_________________________________________________________________________________________________________		

	public Usuario atualizar(UsuarioTO user){
		Usuario usuarioValidado = validacaoAtualizar(user);
		usuarioValidado.setDtNasc(user.getDtNasc());
		usuarioValidado.setGenero(user.getGenero());
		usuarioValidado.setNomeUser(user.getNomeUser());
		usuarioValidado.setPronome(user.getPronome());

		userRepository.save(usuarioValidado);
		return usuarioValidado;
	}

	private Usuario validacaoAtualizar(UsuarioTO user) {
		Usuario usuarioBanco = userRepository.findByEmail(user.getEmail());
		if(usuarioBanco == null) {
			throw new ValidacaoException("USERNFOU");
		}
		if(TipoConta.PACHANGA.getDescricao().equals(user.getTipConta()) && user.getSenha() != null) {
			if(!HashBuilder.compararCodigos(user.getSenha(), usuarioBanco.getSenha())) {
				throw new ValidacaoException("PASSINC2");
			}
			if(user.getSenhaNova() != null) {
				if(HashBuilder.compararCodigos(user.getSenhaNova(), usuarioBanco.getSenha())) {
					throw new ValidacaoException("PASSDIFF");
				}
				usuarioBanco.setSenha(HashBuilder.gerarCodigoHasheado(user.getSenhaNova()));
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
		this.validarUsuario(codUsuario);
		return festaService.funcionalidadeFesta(codFesta, codUsuario);
	}

	public Usuario getUsuarioResponsavelFesta(int codFesta) {
		return userRepository.findByFestaGrupo(codFesta, true);
	}

	public UsuarioFestaTO getInfoUserFesta(Integer codGrupo, Integer codUsuario) {
		Usuario usuario = this.validarUsuario(codUsuario);
		Grupo grupo = this.validarGrupo(codGrupo);
		return usuarioFestaTOFactory.getUsuarioFestaTO(usuario, grupo);
	}

	public Usuario validarUsuario(int codUsuario) {
		Usuario usuario = userRepository.findById(codUsuario);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		return usuario;
	}
	
	public Grupo validarGrupo(int codGrupo) {
		Grupo grupo = grupoRepository.findById(codGrupo);
		if(grupo == null) {
			throw new ValidacaoException("GRUPNFOU");
		}
		return grupo;
	}

}
