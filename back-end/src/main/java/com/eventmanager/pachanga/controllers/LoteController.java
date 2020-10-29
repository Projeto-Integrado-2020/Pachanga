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

import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.dtos.LoteTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.LoteFactory;
import com.eventmanager.pachanga.services.LoteService;

@RestController
@RequestMapping("/lote")
@CrossOrigin
public class LoteController {

	@Autowired
	private LoteService loteService;

	@Autowired
	private LoteFactory loteFactory;

	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaLote(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(loteService.listaLoteFesta(codFesta, codUsuario).stream()
					.map(l -> loteFactory.getLoteTO(l)).collect(Collectors.toList()));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> adicionarLote(@RequestBody LoteTO loteTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			Lote lote = loteService.adicionarLote(loteTo, codUsuario);
			return ResponseEntity.ok(loteFactory.getLoteTO(lote));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> atualizarLote(@RequestBody LoteTO loteTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			Lote lote = loteService.atualizarLote(loteTo, codUsuario);
			return ResponseEntity.ok(loteFactory.getLoteTO(lote));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@ResponseBody
	@DeleteMapping(path = "/remover")
	public ResponseEntity<Object> removerLote(@RequestParam(required = true) int codLote,
			@RequestParam(required = true) int codUsuario) {
		try {
			loteService.removerLote(codLote, codUsuario);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
