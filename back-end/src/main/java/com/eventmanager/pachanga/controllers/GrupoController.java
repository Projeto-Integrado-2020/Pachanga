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
import com.eventmanager.pachanga.services.GrupoService;

@Controller
@RequestMapping("/grupo")
@CrossOrigin
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@ResponseBody
	@PostMapping(path = "/addUserFesta")
	public ResponseEntity<Object> addUsuarioFesta(@RequestParam(required = true)int codFesta, @RequestBody List<String> emails, @RequestParam(required = true)int idUsuario, @RequestParam(required = true)int idGrupo){
		try {
			StringBuilder mensagem = grupoService.addUsuariosFesta(emails, codFesta,idUsuario,idGrupo);
			return ResponseEntity.ok(mensagem);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

}
