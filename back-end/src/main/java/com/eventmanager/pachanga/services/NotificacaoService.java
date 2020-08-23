package com.eventmanager.pachanga.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.domains.NotificacaoGrupo;
import com.eventmanager.pachanga.domains.NotificacaoUsuario;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoFactory;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoGrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoUsuarioRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoStatusNotificacao;

@Transactional
@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ConvidadoRepository convidadoRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private NotificacaoUsuarioRepository notificacaoUsuarioRepository;
	
	@Autowired
	private NotificacaoGrupoRepository notificacaoGrupoRepository;
	
	@Autowired
	private NotificacaoFactory notificacaoFactory;
	

	public NotificacaoTO procurarNotificacaoUsuario(int idUser) {
		this.validacaoUsuario(idUser, null);
		return notificacaoFactory.getNotificacaoTO(notificacaoUsuarioRepository.findAll(), notificacaoGrupoRepository.findAll(), notificacaoRepository.findAll());
	}

	public void deletarNotificacaoConvidado(String emailConvidado, int codNotificacao) {
		this.validarNotificacao(codNotificacao);
		this.validacaoUsuario(0, emailConvidado);
		Convidado convidado = this.validarConvidado(emailConvidado);
		notificacaoRepository.deleteConvidadoNotificacao(convidado.getCodConvidado(), codNotificacao);
	}

	public void deletarNotificacaoGrupo(int codGrupo, int codNotificacao) {
		this.validarNotificacao(codNotificacao);
		this.validarGrupo(codGrupo);
		notificacaoRepository.deleteNotificacaoGrupo(codGrupo, codNotificacao);
	}

	public void inserirNotificacaoGrupo(int codGrupo, int codNotificacao) {
		this.validarNotificacao(codNotificacao);
		this.validarGrupo(codGrupo);
		notificacaoRepository.insertNotificacaoGrupo(codGrupo, codNotificacao);
	}

	public void inserirNotificacaoConvidado(String emailConvidado, int codNotificacao) {
		this.validarNotificacao(codNotificacao);
		this.validacaoUsuario(0, emailConvidado);
		Convidado convidado = this.validarConvidado(emailConvidado);
		notificacaoRepository.insertConvidadoNotificacao(convidado.getCodConvidado(), codNotificacao);
	}

	private Convidado validarConvidado(String emailConvidado) {
		Convidado convidado = convidadoRepository.findByEmail(emailConvidado);
		if(convidado == null) {
			throw new ValidacaoException("CONVNFOU");
		}
		return convidado;
	}

	private void validarGrupo(int codGrupo) {
		Grupo grupo = grupoRepository.findByCod(codGrupo);
		if(grupo == null) {
			throw new ValidacaoException("GRUPNFOU");
		}
	}

	private void validacaoUsuario(int idUser, String emailConvidado) {
		Usuario usuario = null;
		if(idUser != 0) {
			usuario = usuarioRepository.findById(idUser);
		} else {
			usuario = usuarioRepository.findByEmail(emailConvidado);	
		}
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
	}

	private void validarNotificacao(int codNotificacao) {
		Notificacao notificacao = notificacaoRepository.findByCodNotificacao(codNotificacao);
		if(notificacao == null) {
			throw new ValidacaoException("NOTINFOU");
		}
	}

	public void alterarStatus(int idUser, Integer codNotificacao) {
		this.validacaoUsuario(idUser, null);
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioRepository.getNotificacaoUsuario(idUser, codNotificacao);
		if(notificacaoUsuario != null && !notificacaoUsuario.isDestaque()) {
			notificacaoUsuario.setStatus(TipoStatusNotificacao.LIDA.getDescricao());
			notificacaoUsuarioRepository.save(notificacaoUsuario);
		}
	}

	public void deleteNotificacao(int idUser, int codNotificacao) {
		this.validacaoUsuario(idUser, null);
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioRepository.getNotificacaoUsuario(idUser, codNotificacao);
		if(notificacaoUsuario != null ) {
			notificacaoUsuarioRepository.delete(notificacaoUsuario);
		}

	}

	public void destaqueNotificacao(int idUser, int codNotificacao) {
		this.validacaoUsuario(idUser, null);
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioRepository.getNotificacaoUsuario(idUser, codNotificacao);
		if(notificacaoUsuario != null) {
			notificacaoUsuario.setDestaque(!notificacaoUsuario.isDestaque());
			notificacaoUsuarioRepository.save(notificacaoUsuario);
		}
	}
	
	public boolean verificarNotificacaoGrupo(int codGrupo, int codNotificacao) {
		boolean retorno = false;
		this.validarGrupo(codGrupo);
		this.validarNotificacao(codNotificacao);
		
		NotificacaoGrupo notificacaoGrupo = null;
		notificacaoGrupo = notificacaoGrupoRepository.findNotificacaoGrupo(codGrupo, codNotificacao);
		if(notificacaoGrupo != null) retorno = true;

		return retorno;
	}

}
