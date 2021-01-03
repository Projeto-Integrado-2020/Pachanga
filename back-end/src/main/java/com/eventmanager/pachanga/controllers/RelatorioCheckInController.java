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
import com.eventmanager.pachanga.services.RelatorioCheckInService;

@RestController
@RequestMapping("/relatorioCheckIn")
@CrossOrigin
public class RelatorioCheckInController {
	
	@Autowired
	private RelatorioCheckInService relatorioCheckInService;
	
	@ResponseBody
	@GetMapping(path = "/ingressosCompradosEntradas")
	public ResponseEntity<Object> ingressosCompradosEntradas(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioCheckInService.ingressosCompradosEntradas(codFesta, codUsuario));
		}catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@ResponseBody
	@GetMapping(path = "/faixaEtaria")
	public ResponseEntity<Object> listaFaixaEtaria(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioCheckInService.faixaEtariaFesta(codFesta, codUsuario));
		}catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@ResponseBody
	@GetMapping(path = "/generos")
	public ResponseEntity<Object> listaGenerosFesta(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			
			return ResponseEntity.ok(relatorioCheckInService.quantidadeGeneroFesta(codFesta, codUsuario));
		}catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@ResponseBody
	@GetMapping(path = "/quantidadeEntradasHora")
	public ResponseEntity<Object> quantidadeEntradasHora(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			
			return ResponseEntity.ok(relatorioCheckInService.quantidadeEntradaHoras(codFesta, codUsuario));
		}catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}

}
