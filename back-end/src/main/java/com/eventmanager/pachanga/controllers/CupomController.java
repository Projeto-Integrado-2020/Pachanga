package com.eventmanager.pachanga.controllers;

import java.util.List;

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

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.dtos.CupomTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.CupomFactory;
import com.eventmanager.pachanga.services.CupomService;

@RestController
@RequestMapping("/cupom")
@CrossOrigin
public class CupomController {
	
	@Autowired
	private CupomService cupomService;
	
	@ResponseBody
	@GetMapping(path = "/cupomUnico")
	public ResponseEntity<Object> getCupom(@RequestParam(required = true) int idUser, @RequestParam(required = true) int codCupom){
		try {
			Cupom cupom = cupomService.getCupom(codCupom, idUser);
			return ResponseEntity.ok(CupomFactory.getCupomTO(cupom));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	
	@ResponseBody
	@GetMapping(path = "/cuponsFesta")
	public ResponseEntity<Object> getCuponsFesta(@RequestParam(required = true) int idUser, @RequestParam(required = true) int codFesta){
		try {
			List<Cupom> cupons = cupomService.getCuponsFesta(codFesta, idUser);
			return ResponseEntity.ok(CupomFactory.getCuponsTO(cupons));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping(path = "/gerar")
	public ResponseEntity<Object> criarCupom(@RequestBody CupomTO cupomTO, @RequestParam(required = true) int idUser){
		try {
			Cupom cupom = cupomService.gerarCupom(cupomTO, idUser);
			return ResponseEntity.ok(CupomFactory.getCupomTO(cupom));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@DeleteMapping(path = "/excluir")
	public ResponseEntity<Object> removeCupom(@RequestParam(required = true) int idUser, @RequestParam(required = true) int codCupom){
		try {
			Cupom cupom = cupomService.removeCupom(codCupom, idUser);
			return ResponseEntity.ok(CupomFactory.getCupomTO(cupom));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> updateCupom(@RequestBody CupomTO cupomTO, @RequestParam(required = true) int idUser){
		try {
			Cupom cupom = cupomService.atualizarCupom(cupomTO, idUser);
			return ResponseEntity.ok(CupomFactory.getCupomTO(cupom));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	
	

}
