package com.eventmanager.pachanga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.services.ConvidadoService;

@Controller
@RequestMapping("/convidado")
@CrossOrigin
public class ConvidadoController {
	
	@Autowired
	private ConvidadoService convidadoService;
	
	@ResponseBody
	@PostMapping(path = "/addUserFesta")
	public ResponseEntity<Object> addUsuarioFesta(@RequestParam(required = true)int codFesta, @RequestBody List<String> emails, @RequestParam(required = true)int idUsuario, @RequestParam(required = true)int idGrupo){
		try {
			StringBuilder mensagem = convidadoService.addUsuariosFesta(emails, codFesta,idUsuario,idGrupo);
			return ResponseEntity.ok(mensagem);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping(path = "/accConvite")
	public ResponseEntity<Object> aceitarConvite(@RequestParam(required = true)Integer codConvidado, @RequestParam(required = true)Integer idGrupo){
		try {
			convidadoService.aceitarConvite(codConvidado,idGrupo);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping(path = "/recuConvite")
	public ResponseEntity<Object> recusarConvite(@RequestParam(required = true)Integer codConvidado, @RequestParam(required = true)Integer idGrupo){
		try {
			convidadoService.recusarConvite(codConvidado,idGrupo);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping(path = "/reenvConvite")
	public ResponseEntity<Object> reenvConvite(@RequestParam(required = true)int codFesta, @RequestParam(required = true)Integer codConvidado, @RequestParam(required = true)Integer idGrupo, @RequestParam(required = true)int idUsuario){
		try {
			convidadoService.reenvConvite(codConvidado,idGrupo, idUsuario, codFesta);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	
}
