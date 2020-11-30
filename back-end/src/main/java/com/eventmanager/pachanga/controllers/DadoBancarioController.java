package com.eventmanager.pachanga.controllers;

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

import com.eventmanager.pachanga.domains.DadoBancario;
import com.eventmanager.pachanga.dtos.DadoBancarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.DadoBancarioFactory;
import com.eventmanager.pachanga.services.DadoBancarioService;

@RestController
@RequestMapping("/dadoBancario")
@CrossOrigin
public class DadoBancarioController {

	@Autowired
	private DadoBancarioFactory dadoBancarioFactory;

	@Autowired
	private DadoBancarioService dadoBancarioService;

	@ResponseBody
	@GetMapping(path = "/dadoUnico")
	public ResponseEntity<Object> getDadoBancarioTO(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			DadoBancario dadoBancario = dadoBancarioService.dadoBancarioUnico(codFesta, codUsuario);
			return ResponseEntity.ok(dadoBancario == null ? null : dadoBancarioFactory.getDadoBancarioTO(dadoBancario));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> adicionarDadoBancario(@RequestBody DadoBancarioTO dadoBancarioTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(dadoBancarioFactory
					.getDadoBancarioTO(dadoBancarioService.adicionarDadoBancario(dadoBancarioTo, codUsuario)));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> atualizarDadoBancario(@RequestBody DadoBancarioTO dadoBancarioTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(dadoBancarioFactory
					.getDadoBancarioTO(dadoBancarioService.atualizarDadoBancario(dadoBancarioTo, codUsuario)));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/deletar")
	public ResponseEntity<Object> deletarDadoBancario(@RequestBody DadoBancarioTO dadoBancarioTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			dadoBancarioService.deleteDadoBancario(dadoBancarioTo, codUsuario);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
