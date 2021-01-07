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
import com.eventmanager.pachanga.services.RelatorioDeVendaService;

@RestController
@RequestMapping("/relatorioVenda")
@CrossOrigin
public class RelatorioDeVendaController {

	@Autowired
	private RelatorioDeVendaService relatorioDeVendaService;

	@ResponseBody
	@GetMapping(path = "/ingressosFesta")
	public ResponseEntity<Object> relatorioIngressosFesta(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioDeVendaService.relatorioDeIngressos(codFesta, codUsuario));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/ingressosFestaCompradosPagos")
	public ResponseEntity<Object> relatorioIngressosFestaPagos(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioDeVendaService.relatorioDeIngressos(codFesta, codUsuario));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
