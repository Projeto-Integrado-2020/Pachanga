package com.eventmanager.pachanga.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@Autowired
	private NotificacaoFactory notificacaoFactory;

	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaNotificacao(@RequestParam(required = true) int idUser){
		try {
			List<Notificacao> notificacoes = notificacaoService.procurarNotificacaoUsuario(idUser);
			List<NotificacaoTO> notificacoesTo = new ArrayList<>();
			if(!notificacoes.isEmpty()) {
				notificacoesTo = notificacaoFactory.getListNotificacaoTO(notificacoes);
			}
			return ResponseEntity.ok().body(notificacoesTo);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}		
	}
	
	@ResponseBody
	@PutMapping(path = "/mudarStatus")
	public ResponseEntity<Object> mudarStatusNotificacoes(@RequestParam(required = true) int idUser, @RequestBody List<Integer> idNotificacoes){
		try {
			idNotificacoes.stream().forEach(i ->{
				//método para alterar o stauts na tabela notificacoes_X_usuario ou outra coisa
			});
			return ResponseEntity.ok().build();//mudado o status
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}		
	}
	
	@ResponseBody
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deleteNotificacoes(@RequestParam(required = true) int idUser, @RequestParam(required = true) int idNotificacao){
		try {
			//método para deletar a notificacao do cara 
			return ResponseEntity.ok().body("DELENOTI");//delete notificacoes
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}		
	}

}
