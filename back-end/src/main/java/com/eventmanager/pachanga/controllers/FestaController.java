package com.eventmanager.pachanga.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.services.FestaService;

@Controller
@RequestMapping("/festa")
@CrossOrigin
public class FestaController {

	@Autowired
	private FestaService festaService;

	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaFesta(@RequestParam(required = false) String idUser){
		List<Festa> festas = new ArrayList<>();
		try {
			if(idUser == null ) {
				festas =  festaService.procurarFestas();
			}else {
				festas =  festaService.procurarFestasPorUsuario(Integer.parseInt(idUser));
			}
			return ResponseEntity.ok(festas);
		}catch(ValidationException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> addFesta(@RequestBody FestaTO festaTo, @RequestParam(required = true) int idUser){
		try {
			Festa festa = festaService.addFesta(festaTo, idUser);
			return ResponseEntity.ok(festa);
		}catch(ValidationException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deleteFesta(@RequestParam(required = true) int idFesta){
		try {
			festaService.deleteFesta(idFesta);
			return ResponseEntity.ok("FESTDELE");//ver se precisa colocar alguma coisa aqui ou pode mandar somente um ok
		}catch(ValidationException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> atualizaFesta(@RequestBody FestaTO festaTo, @RequestParam(required = true) int idUser){
		
		return null;
	}
}
