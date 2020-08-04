package com.eventmanager.pachanga.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoFactory;
import com.eventmanager.pachanga.services.NotificacaoService;

@Controller
@RequestMapping("/notificacao")
@CrossOrigin
public class NotificacaoController {

	@Autowired
	private NotificacaoService notificacaoService;

	private NotificacaoFactory notificacaoFactory;

	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaNotificacao(@RequestParam(required = true) int idUser){
		try {
			List<Notificacao> notificacoes = notificacaoService.procurarNoticacaoUsuario(idUser);
			List<NotificacaoTO> notificacoesTo = new ArrayList<>();
			if(!notificacoes.isEmpty()) {
				notificacoesTo = notificacaoFactory.getListNotificacaoTO(notificacoes);
			}
			return ResponseEntity.ok().body(notificacoesTo);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}		
	}

}
