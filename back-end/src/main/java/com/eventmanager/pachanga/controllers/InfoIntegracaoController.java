package com.eventmanager.pachanga.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.pachanga.dtos.InfoIntegracaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.InfoIntegracaoFactory;
import com.eventmanager.pachanga.services.InfoIntegracaoService;

@RestController
@RequestMapping("/integracao")
@CrossOrigin
public class InfoIntegracaoController {

	@Autowired
	private InfoIntegracaoFactory infoIntegracaoFactory;

	@Autowired
	private InfoIntegracaoService infoIntegracaoService;

	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaInfoIntegracaoFesta(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(infoIntegracaoService.listaInfoIntegracaoFesta(codFesta, codUsuario).stream()
					.map(i -> infoIntegracaoFactory.getInfoIntegracaoTO(i)).collect(Collectors.toList()));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/infoUnico")
	public ResponseEntity<Object> infoIntegracaoFesta(@RequestBody(required = true) InfoIntegracaoTO infoTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(infoIntegracaoFactory
					.getInfoIntegracaoTO(infoIntegracaoService.infoIntegracaoFesta(infoTo, codUsuario)));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> adicionarinfoIntegracaoFesta(@RequestBody(required = true) InfoIntegracaoTO infoTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(infoIntegracaoFactory
					.getInfoIntegracaoTO(infoIntegracaoService.adicionarinfoIntegracaoFesta(infoTo, codUsuario)));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> atualizarinfoIntegracaoFesta(@RequestBody(required = true) InfoIntegracaoTO infoTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(infoIntegracaoFactory
					.getInfoIntegracaoTO(infoIntegracaoService.atualizarinfoIntegracaoFesta(infoTo, codUsuario)));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ResponseBody
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deleteinfoIntegracaoFesta(@RequestBody(required = true) InfoIntegracaoTO infoTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			infoIntegracaoService.deleteinfoIntegracaoFesta(infoTo, codUsuario);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
