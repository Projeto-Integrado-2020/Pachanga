package com.eventmanager.pachanga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ProblemaFactory;
import com.eventmanager.pachanga.services.ProblemaSevice;

@Controller
@RequestMapping("/problema")
@CrossOrigin
public class ProblemaController {
	
	@Autowired 
	private ProblemaSevice problemaService;
	
	@Autowired
	private ProblemaFactory problemaFactory;
	
	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaProduto() {
		try {
			List<Problema> problemas = problemaService.listarProblemas();
			return ResponseEntity.ok(problemaFactory.getProblemasTO(problemas));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
}
