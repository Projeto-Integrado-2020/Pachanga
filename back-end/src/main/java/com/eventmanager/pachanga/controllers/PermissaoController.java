package com.eventmanager.pachanga.controllers;

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

import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.dtos.PermissaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.PermissaoFactory;
import com.eventmanager.pachanga.services.PermissaoService;

@Controller
@RequestMapping("/permissao")
@CrossOrigin
public class PermissaoController {
	
	@Autowired
	private PermissaoService permissaoService;
	
	//CRUD__________________________________________________________________________________________________________________________________________________________________________________________________
		/*
		@ResponseBody
		@PostMapping(path = "/addPermissao")
		public ResponseEntity<Object> addPermissao(@RequestBody PermissaoTO permissaoTO){
			try {
				Permissao permissao = permissaoService.addPermissao(permissaoTO);
				return ResponseEntity.ok(PermissaoFactory.getPermissaoTO(permissao));
			} catch (ValidacaoException e) {
				return ResponseEntity.status(400).body(e.getMessage());
			}
		}
		
	
		@ResponseBody
		@GetMapping(path = "/getPermissao")
		public ResponseEntity<Object> getPermissao(@RequestParam(required = true)int codPermissao){
			try {
				Permissao permissao = permissaoService.getByIdPermissao(codPermissao);
				return ResponseEntity.ok(PermissaoFactory.getPermissaoTO(permissao));
			} catch (ValidacaoException e) {
				return ResponseEntity.status(400).body(e.getMessage());
			}
		}
		
		@ResponseBody
		@DeleteMapping(path = "/deletePermissao")
		public ResponseEntity<Object> deletePermissao(@RequestParam(required = true)int codPermissao){
			try {
				Permissao permissao = permissaoService.deletarPermissao(codPermissao);
				return ResponseEntity.ok(PermissaoFactory.getPermissaoTO(permissao));
			} catch (ValidacaoException e) {
				return ResponseEntity.status(400).body(e.getMessage());
			}
		}
		
		@ResponseBody
		@PutMapping(path = "/updatePermissao")
		public ResponseEntity<Object> updateGrupoFesta(@RequestBody PermissaoTO permissaoTO){
			try {
				Permissao permissao = permissaoService.alterar(permissaoTO);
				if(permissao != null) {
					return ResponseEntity.ok(PermissaoFactory.getPermissaoTO(permissao));
				}
				return ResponseEntity.status(400).body("Permissao duplicada ou inválida");
			} catch (ValidacaoException e) {
				return ResponseEntity.status(400).body(e.getMessage());
			}
		}
		 */
}

