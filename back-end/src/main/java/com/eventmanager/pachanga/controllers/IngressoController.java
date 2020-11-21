package com.eventmanager.pachanga.controllers;

import java.util.ArrayList;
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

import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.services.IngressoService;

@Controller
@RequestMapping("/ingresso")
@CrossOrigin
public class IngressoController {
	
	@Autowired
	private IngressoService ingressoService;
	
	@Autowired
	private IngressoFactory ingressoFactory;


	
	@ResponseBody
	@GetMapping(path = "/listaUser")
	public ResponseEntity<Object> getIngressosUser(@RequestParam(required = true) int idUser){
		try {
			List<Ingresso> ingressos = ingressoService.getIngressosUser(idUser);
			List<IngressoTO> ingressosTO = new ArrayList<>();
			for(Ingresso ingresso : ingressos) {
			IngressoTO ingressoTO = ingressoFactory.getIngressoTO(ingresso);
			ingressoTO.setFesta(ingressoService.getFestaIngressoUser(ingressoTO.getCodFesta()));
			ingressosTO.add(ingressoTO);			
			}
		return ResponseEntity.ok(ingressosTO);
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/listaFesta")
	public ResponseEntity<Object> getIngressosFesta(@RequestParam(required = true) int idFesta){
		try {
			List<Ingresso> ingressos = ingressoService.getIngressosFesta(idFesta);
			List<IngressoTO> ingressosTO = new ArrayList<>();
			for(Ingresso ingresso : ingressos) {
			IngressoTO ingressoTO = ingressoFactory.getIngressoTO(ingresso);
			ingressosTO.add(ingressoTO);			
			}
		return ResponseEntity.ok(ingressosTO);
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping(path = "/addIngresso")
	public ResponseEntity<Object> addIngresso(@RequestBody IngressoTO ingressoTO){
	try {
		ingressoService.addIngresso(ingressoTO);
		return ResponseEntity.ok(ingressoTO);
	}catch(ValidacaoException e) {
		return ResponseEntity.status(400).body(e.getMessage());
	}
	}

	@ResponseBody
	@PostMapping(path = "/addIngressoLista")
	public ResponseEntity<Object> addIngressoLista(@RequestBody List<IngressoTO> listaIngressoTO){
	try {
		for(IngressoTO ingresso : listaIngressoTO){
		ingressoService.addIngresso(ingresso);
		}
		return ResponseEntity.ok(listaIngressoTO);
	}catch(ValidacaoException e) {
		return ResponseEntity.status(400).body(e.getMessage());
	}
	}
	
	@ResponseBody
	@PutMapping(path = "/updateCheckin")
	public ResponseEntity<Object> updateChekin(@RequestBody IngressoTO ingressoTO){
		try {
			ingressoService.updateCheckin(ingressoTO);
			return ResponseEntity.ok(ingressoTO);
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PutMapping(path = "/updateStatusCompra")
	public ResponseEntity<Object> updateStatusCompra(@RequestBody IngressoTO ingressoTO){
		try {
			ingressoService.updateStatusCompra(ingressoTO);
			return ResponseEntity.ok(ingressoTO);
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
}
