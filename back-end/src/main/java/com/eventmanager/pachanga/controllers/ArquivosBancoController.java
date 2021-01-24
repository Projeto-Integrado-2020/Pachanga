package com.eventmanager.pachanga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.services.ArquivosBancoService;

@RestController
@RequestMapping("/arqBanco")
@CrossOrigin
public class ArquivosBancoController {

	@Autowired
	private ArquivosBancoService arquivosBancoService;

	@ResponseBody
	@GetMapping(path = "/cria")
	public ResponseEntity<Object> criaArquivoBanco() {
		try {
			arquivosBancoService.criacaoRemessa();
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
