package com.eventmanager.pachanga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.services.RelatorioAreaSegurancaService;

@RestController
@RequestMapping("/relatorioAreaSeguranca")
@CrossOrigin
public class RelatorioAreaSegurancaController {
	
	@Autowired
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;
	
	@ResponseBody
	@GetMapping(path = "/problemasArea")
	public ResponseEntity<Object> relatorioProblemasArea(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioAreaSegurancaService.relatorioProblemasArea(codFesta, codUsuario));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/chamadasUsuario")
	public ResponseEntity<Object> relatorioChamadasUsuario(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioAreaSegurancaService.relatorioChamadasUsuario(codFesta, codUsuario));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@ResponseBody
	@GetMapping(path = "/usuarioSolucionador")
	public ResponseEntity<Object> relatorioUsuarioSolucionador(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioAreaSegurancaService.relatorioUsuarioSolucionador(codFesta, codUsuario));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
