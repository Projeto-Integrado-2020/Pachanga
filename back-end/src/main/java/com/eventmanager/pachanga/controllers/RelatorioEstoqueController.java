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
import com.eventmanager.pachanga.services.RelatorioEstoqueService;

@RestController
@RequestMapping("/relatorioEstoque")
@CrossOrigin
public class RelatorioEstoqueController {

	@Autowired
	private RelatorioEstoqueService relatorioEstoqueService;

	@ResponseBody
	@GetMapping(path = "/consumoItemEstoque")
	public ResponseEntity<Object> relatorioConsumoItemEstoque(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioEstoqueService.relatoriosEstoque(codFesta, codUsuario, 1));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@ResponseBody
	@GetMapping(path = "/perdaItemEstoque")
	public ResponseEntity<Object> relatorioPerdaItemEstoque(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioEstoqueService.relatoriosEstoque(codFesta, codUsuario, 2));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/quantidadeItemEstoque")
	public ResponseEntity<Object> relatorioQuantidadeItemEstoque(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(relatorioEstoqueService.relatoriosEstoque(codFesta, codUsuario, 3));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
