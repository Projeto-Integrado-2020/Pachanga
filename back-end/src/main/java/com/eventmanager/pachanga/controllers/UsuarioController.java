package com.eventmanager.pachanga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.UsuarioFactory;
import com.eventmanager.pachanga.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioController{
	
	@Autowired
	private UsuarioService userService;

	@PostMapping(path ="/cadastro")
	public ResponseEntity<Object> cadastro(@RequestBody UsuarioTO user) {
		try {
			Usuario userCadastrado = userService.cadastrar(user);
			UsuarioTO userto = UsuarioFactory.getUsuarioTO(userCadastrado);
			return ResponseEntity.ok(userto);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());	
		}
	}


	@PostMapping(path ="/login")
	public ResponseEntity<Object> login(@RequestBody UsuarioTO user){
		try {
			Usuario usarioLogin = userService.logar(user);
			UsuarioTO userto = UsuarioFactory.getUsuarioTO(usarioLogin);
			return ResponseEntity.ok(userto);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@PutMapping(path ="/atualizacao")
	public ResponseEntity<Object> atualiza(@RequestBody UsuarioTO user) {
		try {
			Usuario userAtualizado = userService.atualizar(user);
			UsuarioTO userto = UsuarioFactory.getUsuarioTO(userAtualizado);
			return ResponseEntity.ok(userto);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());	
		}
	}

}
