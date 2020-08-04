package com.eventmanager.pachanga.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.NotificacaoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;

@Transactional
@Service
public class NotificacaoService {
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Notificacao> procurarNoticacaoUsuario(int idUser) {
		this.validacaoUserNotificacao(idUser);
		return notificacaoRepository.findByUserId(idUser);
	}
	
	private void validacaoUserNotificacao(int idUser) {
		Usuario usuario = usuarioRepository.findById(idUser);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
	}

}
