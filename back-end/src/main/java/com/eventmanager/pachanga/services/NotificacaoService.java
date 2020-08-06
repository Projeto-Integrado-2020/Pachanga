package com.eventmanager.pachanga.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;

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

	public List<Notificacao> procurarNotificacaoUsuario(int idUser) {
		this.validacaoUsuario(idUser, null);
		List<Notificacao> notificacoes = new ArrayList<>();
		notificacoes.addAll(notificacaoRepository.findNotificacaoGrupoByUserId(idUser));
		notificacoes.addAll(notificacaoRepository.findConvidadoNotificacaoByUserId(idUser));
		return notificacoes;
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

}
