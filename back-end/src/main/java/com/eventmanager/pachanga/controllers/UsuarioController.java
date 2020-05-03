package com.eventmanager.pachanga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController{
	
	@Autowired
	private UsuarioRepository userRepository;
	
	private final UsuarioService userService = new UsuarioService();
	
	@RequestMapping(path ="/cadastro", method = RequestMethod.POST)
	public ResponseEntity<UsuarioTO> cadastro(@RequestBody Usuario user) {
		Boolean usuarioJaCadastrado = userService.cadastro(user, userRepository);
		if(usuarioJaCadastrado) {
			UsuarioTO userto = new UsuarioTO(user.getNomeUser(), user.getTipConta(), user.getDtNasc(), user.getSexo());
			return new ResponseEntity<UsuarioTO>(userto, HttpStatus.OK);
		}
		return new ResponseEntity<UsuarioTO>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(path ="/login", method = RequestMethod.POST)
	public ResponseEntity<UsuarioTO> login(@RequestBody Usuario user) {
		Usuario usarioLogin = userService.login(user, userRepository);
		if(usarioLogin != null) {
			UsuarioTO userto = new UsuarioTO(usarioLogin.getNomeUser(), usarioLogin.getTipConta(), usarioLogin.getDtNasc(), usarioLogin.getSexo());
			return new ResponseEntity<UsuarioTO>(userto, HttpStatus.OK);
		}
		return new ResponseEntity<UsuarioTO>(HttpStatus.BAD_REQUEST);
	}


}
