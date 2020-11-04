package com.eventmanager.pachanga.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.dtos.AreaSegurancaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaFactory;
import com.eventmanager.pachanga.services.AreaSegurancaService;

@RestController
@RequestMapping("/areaSeguranca")
@CrossOrigin
public class AreaSegurancaController {

	@Autowired
	private AreaSegurancaService areaSegurancaService;

	@Autowired
	private AreaSegurancaFactory areaSegurancaFactory;

	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaAreaSegurancaFesta(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			Map<AreaSeguranca, List<AreaSegurancaProblema>> areasSegurancaProblema = areaSegurancaService.listaAreaSegurancaFesta(codFesta, codUsuario);
			List<AreaSegurancaTO> areasTo = new ArrayList<>();
			for (Map.Entry<AreaSeguranca, List<AreaSegurancaProblema>> areaSegurancaProblema : areasSegurancaProblema.entrySet()) {
				areasTo.add(areaSegurancaFactory.getAreaTo(areaSegurancaProblema.getKey(), areaSegurancaProblema.getValue()));
			}
			return ResponseEntity.ok(areasTo);
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> criarAreaSegurancaFesta(@RequestBody AreaSegurancaTO areaSegurancaTo,
			@RequestParam(required = true) int codUsuario) {
		try {
			AreaSeguranca areaSeguranca = areaSegurancaService.criacaoAreSegurancaFesta(codUsuario,
					areaSegurancaFactory.getArea(areaSegurancaTo));

			return ResponseEntity.ok(areaSegurancaFactory.getAreaTo(areaSeguranca));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> atualizarAreaSegurancaFesta(@RequestParam(required = true) int codUsuario,
			@RequestBody AreaSegurancaTO areaSegurancaTo) {
		try {
			AreaSeguranca areaSeguranca = areaSegurancaService.atualizarAreSegurancaFesta(codUsuario,
					areaSegurancaFactory.getArea(areaSegurancaTo));

			return ResponseEntity.ok(areaSegurancaFactory.getAreaTo(areaSeguranca));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deletarAreaSegurancaFesta(@RequestParam(required = true) int codUsuario,
			@RequestParam(required = true) int codArea) {
		try {
			areaSegurancaService.deletarAreSegurancaFesta(codUsuario, codArea);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
