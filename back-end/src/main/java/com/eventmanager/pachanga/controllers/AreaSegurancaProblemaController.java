package com.eventmanager.pachanga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaProblemaFactory;
import com.eventmanager.pachanga.services.AreaSegurancaProblemaService;

@Controller
@RequestMapping("/areaSegurancaProblema")
@CrossOrigin
public class AreaSegurancaProblemaController {
	
	@Autowired
	private AreaSegurancaProblemaService areaSegurancaProblemaService;
	
	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> addProblemaSeguranca(@RequestBody AreaSegurancaProblemaTO problemaSegurancaTO, @RequestParam(required = true) int idUsuario){
		try {
			AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaService.addProblemaSeguranca(problemaSegurancaTO, idUsuario);
			return ResponseEntity.ok(AreaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(problemaSeguranca));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> updateProblemaSeguranca(@RequestBody AreaSegurancaProblemaTO problemaSegurancaTO, @RequestParam(required = true) int idUsuario){
		try {
			AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaService.updateProblemaSeguranca(problemaSegurancaTO, idUsuario);
			return ResponseEntity.ok(AreaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(problemaSeguranca));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/remover")
	public ResponseEntity<Object> deleteProblemaSeguranca(@RequestParam(required = true) int codAreaSeguranca, @RequestParam(required = true) int codProblema, @RequestParam(required = true) int codFesta, @RequestParam(required = true) int idUsuario){
		try {
			AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaService.deleteProblemaSeguranca(codAreaSeguranca, codProblema, codFesta, idUsuario);
			return ResponseEntity.ok(AreaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(problemaSeguranca));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/findProblemaSeguranca")
	public ResponseEntity<Object> findProblemaSeguranca(@RequestParam(required = true) int codAreaSeguranca, @RequestParam(required = true) int codProblema, @RequestParam(required = true) int codFesta, @RequestParam(required = true) int idUsuario){
		try {
			AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaService.findProblemaSeguranca(codAreaSeguranca, codProblema, codFesta, idUsuario);
			return ResponseEntity.ok(AreaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(problemaSeguranca));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/ findAllProblemasSegurancaArea")
	public ResponseEntity<Object>  findAllProblemasSegurancaArea(@RequestParam(required = true) int codAreaSeguranca, @RequestParam(required = true) int codFesta, @RequestParam(required = true) int idUsuario){
		try {
			List<AreaSegurancaProblema> problemasSeguranca = areaSegurancaProblemaService.findAllProblemasSegurancaArea(codAreaSeguranca, codFesta, idUsuario);
			return ResponseEntity.ok(AreaSegurancaProblemaFactory.getProblemasSegurancaTO(problemasSeguranca));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/ findAllProblemasSegurancaFesta")
	public ResponseEntity<Object>  findAllProblemasSegurancaFesta(@RequestParam(required = true) int codFesta, @RequestParam(required = true) int idUsuario){
		try {
			List<AreaSegurancaProblema> problemasSeguranca = areaSegurancaProblemaService.findAllProblemasSegurancaFesta(codFesta, idUsuario);
			return ResponseEntity.ok(AreaSegurancaProblemaFactory.getProblemasSegurancaTO(problemasSeguranca));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	
	
	
	
	
}
