package com.eventmanager.pachanga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.PermissaoFactory;
import com.eventmanager.pachanga.services.PermissaoService;

@Controller
@RequestMapping("/permissao")
@CrossOrigin
public class PermissaoController {
	
	@Autowired
	private PermissaoService permissaoService;
	
		@ResponseBody
		@GetMapping(path = "/getAllPermissao")
		public ResponseEntity<Object> getAllPermissao(){
			try {
				List<Permissao> permissao = permissaoService.getAllPermissao();
				return ResponseEntity.ok(PermissaoFactory.getPermissoesTO(permissao));
			} catch (ValidacaoException e) {
				return ResponseEntity.status(400).body(e.getMessage());
			}
		}
		
	
}

