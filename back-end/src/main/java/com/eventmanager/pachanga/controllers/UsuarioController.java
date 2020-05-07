package com.eventmanager.pachanga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioController{

	@Autowired
	private UsuarioService userService;

	@RequestMapping(path ="/cadastro", method = RequestMethod.POST)
	public ResponseEntity<Object> cadastro(@RequestBody Usuario user) {
		try {
			userService.cadastro(user);
			UsuarioTO userto = criadorUserDto(user);
			return ResponseEntity.ok(userto);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());	
		}
	}

	@RequestMapping(path ="/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Usuario user) {
		System.out.println(user.getEmail());
		try {
			Usuario usarioLogin = userService.login(user);
			UsuarioTO userto = criadorUserDto(usarioLogin);
			return ResponseEntity.ok(userto);			
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body("Usuário ou senha incorretos");
		}
	}
	
	private UsuarioTO criadorUserDto(Usuario user) {
		return new UsuarioTO(user.getNomeUser(), user.getDtNasc());
	}

}
